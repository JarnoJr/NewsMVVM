package com.example.newsmvvm.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsmvvm.R
import com.example.newsmvvm.adapter.BreakingNewsAdapter
import com.example.newsmvvm.adapter.NewsLoadStateAdapter
import com.example.newsmvvm.databinding.BreakingNewsFragmentBinding
import com.example.newsmvvm.model.Article
import com.example.newsmvvm.model.DialogItem
import com.example.newsmvvm.util.OnArticleClickListener
import com.example.newsmvvm.viewmodel.BreakingNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BreakingNewsFragment :
    BaseFragment<BreakingNewsFragmentBinding>(BreakingNewsFragmentBinding::inflate),
    OnArticleClickListener {
    private val mViewModel by viewModels<BreakingNewsViewModel>()

    @Inject
    lateinit var mAdapter: BreakingNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeViewModel()
        initRecycler()
        val backStackEntry = findNavController().getBackStackEntry(R.id.breakingNewsFragment2)
        backStackEntry.savedStateHandle.getLiveData<DialogItem>("category")
            .observe(viewLifecycleOwner) { category ->
                binding.tvCategory.text = category.title
            }
        backStackEntry.savedStateHandle.getLiveData<String>("country")
            .observe(viewLifecycleOwner) { country ->
                binding.countryCode.text = country
            }
        binding.countryCode.text = mViewModel.getCountry()
        binding.tvCategory.text = mViewModel.getCategory()


        binding.loadingOrErrorLayout.apply {
            buttonRetry.setOnClickListener {
                mAdapter.retry()
            }
        }
        initToolbar()
        observeTextFields()
    }

    private fun initAdapter() {
        mAdapter.setOnClickListener(this)
        mAdapter.addLoadStateListener { loadState ->
            binding.apply {
                loadingOrErrorLayout.progressBar.isVisible =
                    loadState.source.refresh is LoadState.Loading
                rvBreakingNews.isVisible = loadState.source.refresh is LoadState.NotLoading
                loadingOrErrorLayout.buttonRetry.isVisible =
                    loadState.source.refresh is LoadState.Error
                loadingOrErrorLayout.textViewError.isVisible =
                    loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && mAdapter.itemCount < 1) {
                    rvBreakingNews.isVisible = false
                    loadingOrErrorLayout.textViewEmpty.isVisible = true
                } else {
                    loadingOrErrorLayout.textViewEmpty.isVisible = false
                }

            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.category -> {
                    findNavController().navigate(
                        BreakingNewsFragmentDirections.actionBreakingNewsFragment2ToDialogFragment(
                            mViewModel.displayCategories().toTypedArray()
                        )
                    )
                    true
                }
                R.id.country -> {
                    findNavController().navigate(
                        BreakingNewsFragmentDirections.actionBreakingNewsFragment2ToDialogFragment(
                            mViewModel.displayCountries().toTypedArray()
                        )
                    )
                    true
                }
                else -> false
            }
        }
    }

    private fun initRecycler() {
        binding.rvBreakingNews.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter { mAdapter.retry() },
                footer = NewsLoadStateAdapter { mAdapter.retry() }
            )
        }
    }

    private fun observeTextFields() {
        binding.tvCategory.addTextChangedListener {
            mViewModel.setCategory(it.toString().toLowerCase())
        }
        binding.countryCode.addTextChangedListener {
            mViewModel.setCountry(it.toString().toLowerCase())
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenCreated {
            mViewModel.news.observe(viewLifecycleOwner) { news ->
                mAdapter.submitData(viewLifecycleOwner.lifecycle, news)
            }
        }
    }


    override fun onArticleClick(article: Article) {
        val action =
            BreakingNewsFragmentDirections.actionBreakingNewsFragment2ToArticleFragment(article)
        findNavController().navigate(action)
    }
}