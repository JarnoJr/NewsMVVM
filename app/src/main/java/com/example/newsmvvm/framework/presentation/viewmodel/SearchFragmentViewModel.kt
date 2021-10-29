package com.example.newsmvvm.framework.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.newsmvvm.business.domain.model.ArticleWithSeparator
import com.example.newsmvvm.business.domain.model.UiAction
import com.example.newsmvvm.business.domain.model.UiState
import com.example.newsmvvm.business.usecase.SearchNewsUseCase
import com.example.newsmvvm.util.Constants.DEFAULT_QUERY
import com.example.newsmvvm.util.Constants.LAST_QUERY_SCROLLED
import com.example.newsmvvm.util.Constants.LAST_SEARCH_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchFragmentViewModel"

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val useCase: SearchNewsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: StateFlow<UiState>

    val pagingDataFlow: Flow<PagingData<ArticleWithSeparator>>

    val accept: (UiAction) -> Unit

    init {
        val initialQuery: String = savedStateHandle.get(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        val lastQueryScrolled: String = savedStateHandle.get(LAST_QUERY_SCROLLED) ?: DEFAULT_QUERY
        val actionStateFlow = MutableSharedFlow<UiAction>()
        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search(query = initialQuery)) }
        val queriesScrolled = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(UiAction.Scroll(currentQuery = lastQueryScrolled)) }


        pagingDataFlow = searches
            .flatMapLatest { searchArticles(query = it.query) }
            .cachedIn(viewModelScope)

        state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            UiState(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState()
            )
        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }


    private fun searchArticles(query: String) = useCase.getSearchResultStream(query)
        .map { pagingData -> pagingData.map { ArticleWithSeparator.ArticleItem(it) } }
        .map {
            it.insertSeparators { before, after ->
                if (after == null) {
                    return@insertSeparators null
                }
                if (before == null) {
                    return@insertSeparators ArticleWithSeparator.Separator(query)

                } else {
                    null
                }
            }
        }

    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value.query
        savedStateHandle[LAST_QUERY_SCROLLED] = state.value.lastQueryScrolled
        super.onCleared()
    }
}