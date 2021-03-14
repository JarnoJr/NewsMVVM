package com.example.newsmvvm.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsmvvm.R
import com.example.newsmvvm.adapter.BreakingNewsAdapter
import com.example.newsmvvm.adapter.NewsLoadStateAdapter
import com.example.newsmvvm.databinding.BreakingNewsFragmentBinding
import com.example.newsmvvm.viewmodel.BreakingNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.breaking_news_fragment) {

    private val mViewModel by viewModels<BreakingNewsViewModel>()

    @Inject
    lateinit var mAdapter: BreakingNewsAdapter

    private var _mBinding: BreakingNewsFragmentBinding? = null

    private val mBinding get() = _mBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _mBinding = BreakingNewsFragmentBinding.bind(view)
        initRecycler()
        observeViewModel()
        mViewModel.setCategory("business")
        mViewModel.setCountry("us")

        mBinding.buttonRetry.setOnClickListener {
            mAdapter.retry()
        }

        mAdapter.addLoadStateListener { combinedLoadStates ->
            mBinding.apply {
                paginationProgressBar.isVisible =
                    combinedLoadStates.source.refresh is LoadState.Loading
                rvBreakingNews.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = combinedLoadStates.source.refresh is LoadState.Error
                tvErrorMessage.isVisible = combinedLoadStates.source.refresh is LoadState.Error

                if (combinedLoadStates.source.refresh is LoadState.NotLoading && combinedLoadStates.append.endOfPaginationReached && mAdapter.itemCount < 1) {
                    rvBreakingNews.isVisible = false
                }

            }
        }
    }

    private fun initRecycler() {
        mBinding.rvBreakingNews.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter { mAdapter.retry() },
                footer = NewsLoadStateAdapter { mAdapter.retry() }
            )
        }

    }

    private fun observeViewModel() {
        mViewModel.news.observe(viewLifecycleOwner) { news ->
            mAdapter.submitData(viewLifecycleOwner.lifecycle, news)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }


}