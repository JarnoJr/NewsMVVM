package com.example.newsmvvm.framework.presentation.view.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsmvvm.R
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.domain.model.DialogItem
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
import com.example.newsmvvm.util.getNavigationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

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
        mAdapter.setOnClickListener(this)
        initToolbarMenu()
    }

    private fun initRecycler() {
        binding.adapter = mAdapter
    }

    private fun subscribeObservers() {
        launchOnLifecycleScope {
            mViewModel.articles.collectLatest {
                mAdapter.submitData(it)
            }
        }
        getNavigationResult<DialogItem>(R.id.breakingNewsFragment2, Constants.CATEGORY_KEY) {
            mViewModel.setCategory(it.text)
        }
        getNavigationResult<DialogItem>(R.id.breakingNewsFragment2, COUNTRY_KEY) {
            mViewModel.setCountry(it.text)
        }
    }

    private fun initToolbarMenu() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.country -> {
                    showDialog(getCountries(requireContext()))
                }
                R.id.category -> {
                    showDialog(getCategories(requireContext()))
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
        // TODO: 26.10.2021 handle on click
    }

    override val layoutId: Int
        get() = R.layout.breaking_news_fragment

    override fun getVM(): BreakingNewsViewModel = mViewModel


}