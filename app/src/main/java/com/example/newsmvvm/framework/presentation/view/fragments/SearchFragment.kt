package com.example.newsmvvm.framework.presentation.view.fragments

import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmvvm.R
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.domain.model.ArticleWithSeparator
import com.example.newsmvvm.business.domain.model.UiAction
import com.example.newsmvvm.business.domain.model.UiState
import com.example.newsmvvm.databinding.SearchNewsFragmentBinding
import com.example.newsmvvm.framework.adapter.BreakingNewsAdapter
import com.example.newsmvvm.framework.adapter.NewsLoadStateAdapter
import com.example.newsmvvm.framework.adapter.SearchAdapter
import com.example.newsmvvm.framework.presentation.base.BaseFragment
import com.example.newsmvvm.framework.presentation.viewmodel.SearchFragmentViewModel
import com.example.newsmvvm.util.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchNewsFragmentBinding, SearchFragmentViewModel>(),ItemClickListener<ArticleWithSeparator.ArticleItem> {

    private val mViewModel: SearchFragmentViewModel by viewModels()

    @Inject
    lateinit var mAdapter: SearchAdapter

    override fun init() {
        mAdapter.setClickListener(this)
        bindState(
            uiState = mViewModel.state,
            pagingData = mViewModel.pagingDataFlow,
            uiActions = mViewModel.accept
        )
    }

    private fun bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<ArticleWithSeparator>>,
        uiActions: (UiAction) -> Unit
    ) {
        val header = NewsLoadStateAdapter { mAdapter.retry() }
        binding.adapter = mAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = NewsLoadStateAdapter { mAdapter.retry() }
        )
        bindSearch(uiState = uiState, onQueryChanged = uiActions)

        bindList(uiState = uiState, pagingData = pagingData, onScrollChanged = uiActions)
    }

    private fun bindSearch(uiState: StateFlow<UiState>, onQueryChanged: (UiAction.Search) -> Unit) {
        val et = binding.searchView.findViewById<EditText>(R.id.search_src_text) as EditText
        et.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateArticlesFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }
        et.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateArticlesFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }
    }

    private fun updateArticlesFromInput(onQueryChanged: (UiAction.Search) -> Unit) {
        val et = binding.searchView.findViewById<EditText>(R.id.search_src_text) as EditText
        et.text.trim().let {
            if (it.isNotEmpty()) {
                binding.searchRv.scrollToPosition(0)
                onQueryChanged(UiAction.Search(query = it.toString()))
                et.setText("")
            }
        }
    }

    private fun bindList(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<ArticleWithSeparator>>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
        binding.searchRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
            }
        })
        val notLoading = mAdapter.loadStateFlow.distinctUntilChangedBy { it.source.refresh }
            .map { it.source.refresh is LoadState.NotLoading }

        val hasNotScrolledForCurrentSearch =
            uiState.map { it.hasNotScrolledForCurrentSearch }.distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        ).distinctUntilChanged()

        launchOnLifecycleScope {
            pagingData.collectLatest(mAdapter::submitData)
        }
        launchOnLifecycleScope {
            shouldScrollToTop.collect {
                if (it) binding.searchRv.scrollToPosition(0)
            }
        }
        launchOnLifecycleScope {
            binding.loadingOrErrorLayout.buttonRetry.setOnClickListener{
                mAdapter.retry()
            }
            mAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && mAdapter.itemCount == 0
                binding.apply {
                    searchRv.isVisible = !isListEmpty
                    showProgress(loadState.source.refresh is LoadState.Loading)
                    loadingOrErrorLayout.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error

                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error
                    errorState?.let {
                        Toast.makeText(
                            requireContext(),
                            "\uD83D\uDE28 Wooops ${it.error}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
    override val layoutId: Int
        get() = R.layout.search_news_fragment

    override fun getVM(): SearchFragmentViewModel = mViewModel

    override fun onClick(item: ArticleWithSeparator.ArticleItem) {
        val action = SearchFragmentDirections.actionSearchFragmentToArticleFragment(item.article)
        findNavController().navigate(action)
    }
}