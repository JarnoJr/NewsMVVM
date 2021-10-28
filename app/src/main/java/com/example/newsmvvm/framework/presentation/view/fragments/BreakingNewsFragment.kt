package com.example.newsmvvm.framework.presentation.view.fragments

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.newsmvvm.R
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.domain.model.DialogItem
import com.example.newsmvvm.business.domain.util.RemotePresentationState
import com.example.newsmvvm.business.domain.util.getCategories
import com.example.newsmvvm.business.domain.util.getCountries
import com.example.newsmvvm.databinding.BreakingNewsFragmentBinding
import com.example.newsmvvm.framework.adapter.BreakingNewsAdapter
import com.example.newsmvvm.framework.adapter.NewsLoadStateAdapter
import com.example.newsmvvm.framework.presentation.base.BaseFragment
import com.example.newsmvvm.framework.presentation.viewmodel.BreakingNewsViewModel
import com.example.newsmvvm.util.Constants
import com.example.newsmvvm.util.Constants.COUNTRY_KEY
import com.example.newsmvvm.util.ItemClickListener
import com.example.newsmvvm.util.asRemotePresentationState
import com.example.newsmvvm.util.getNavigationResult
import com.neovisionaries.i18n.CountryCode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "BreakingNewsFragment"

@AndroidEntryPoint
class BreakingNewsFragment :
    BaseFragment<BreakingNewsFragmentBinding, BreakingNewsViewModel>(),
    ItemClickListener<Article> {

    private val mViewModel by viewModels<BreakingNewsViewModel>()

    @Inject
    lateinit var mAdapter: BreakingNewsAdapter

    override fun init() {
        initRecycler()
        subscribeObservers()
        initToolbarMenu()
    }

    private fun initRecycler() {
        binding.adapter = mAdapter.withLoadStateHeaderAndFooter(
            header = NewsLoadStateAdapter { mAdapter.retry() },
            footer = NewsLoadStateAdapter { mAdapter.retry() }
        )
        mAdapter.apply {
            loadStateFlow.asRemotePresentationState()
                .map { it == RemotePresentationState.PRESENTED }
            setOnClickListener(this@BreakingNewsFragment)
            addLoadStateListener { loadState ->
                val isEmpty = loadState.refresh is LoadState.NotLoading && itemCount == 0
                binding.errorLayout.buttonRetry.isVisible =
                    loadState.mediator?.refresh is LoadState.Error && mAdapter.itemCount == 0
                binding.rvBreakingNews.isVisible =
                    loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                binding.errorLayout.textViewError.isVisible = isEmpty
                showProgress(loadState.mediator?.refresh is LoadState.Loading)
            }
        }
    }

    private fun subscribeObservers() {
        mViewModel.articles.observe(viewLifecycleOwner) {
            launchOnLifecycleScope {
                mAdapter.submitData(it)
            }
        }
        getNavigationResult<DialogItem>(R.id.breakingNewsFragment2, Constants.CATEGORY_KEY) {
            mViewModel.updateCategory(it.text)
        }
        getNavigationResult<DialogItem>(R.id.breakingNewsFragment2, COUNTRY_KEY) {
            mViewModel.updateCountry(it.text)
        }
    }

    private fun initToolbarMenu() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.country -> {
                    val countries = getCountries(requireContext())
                        .map { country ->
                            if (country.text == CountryCode.getByAlpha2Code(mViewModel.selectedCountry.uppercase())
                                    .getName()
                            ) {
                                country.isChecked = true
                            }
                            country
                        }
                    showDialog(countries)
                }
                R.id.category -> {
                    val categories = getCategories(requireContext())
                        .map { category ->
                            if (category.text.lowercase() == mViewModel.selectedCategory) {
                                category.isChecked = true
                            }
                            category
                        }
                    showDialog(categories)
                }
            }
            true
        }
    }

    private fun showDialog(list: List<DialogItem>) {
        val action =
            BreakingNewsFragmentDirections.actionBreakingNewsFragment2ToDialogFragment(list.toTypedArray())
        findNavController().navigate(action)
    }

    override fun onClick(item: Article) {
        val action =
            BreakingNewsFragmentDirections.actionBreakingNewsFragment2ToArticleFragment(item)
        findNavController().navigate(action)
    }

    override val layoutId: Int
        get() = R.layout.breaking_news_fragment

    override fun getVM(): BreakingNewsViewModel = mViewModel


}