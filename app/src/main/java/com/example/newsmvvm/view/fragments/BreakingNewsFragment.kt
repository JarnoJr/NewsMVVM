package com.example.newsmvvm.view.fragments

import android.app.Dialog
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
import com.example.newsmvvm.adapter.DialogAdapter
import com.example.newsmvvm.adapter.NewsLoadStateAdapter
import com.example.newsmvvm.adapter.OnItemClickListener
import com.example.newsmvvm.databinding.BreakingNewsFragmentBinding
import com.example.newsmvvm.model.Article
import com.example.newsmvvm.model.DialogItem
import com.example.newsmvvm.util.OnArticleClickListener
import com.example.newsmvvm.viewmodel.BreakingNewsViewModel
import com.neovisionaries.i18n.CountryCode
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "BreakingNewsFragment"

@AndroidEntryPoint
class BreakingNewsFragment :
    BaseFragment<BreakingNewsFragmentBinding>(BreakingNewsFragmentBinding::inflate),
    OnItemClickListener,
    OnArticleClickListener {
    private val mViewModel by viewModels<BreakingNewsViewModel>()

    @Inject
    lateinit var mAdapter: BreakingNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeViewModel()
        initRecycler()
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
                    showCategoryDialog()
                    true
                }
                R.id.country -> {
                    showCountryDialog()
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

    private fun showCategoryDialog() {
        initDialog(1)
    }

    private fun showCountryDialog() {
        initDialog(2)
    }

    private fun initDialog(type: Int) {
        val dialog = Dialog(requireContext(), R.style.Theme_AppCompat_Light_Dialog_Alert)
        val view = layoutInflater.inflate(R.layout.menu_dialog, null)
        val dialogAdapter = DialogAdapter(this, dialog)
        if (type == 1) dialogAdapter.displayCategories() else dialogAdapter.displayCountries()
        val binding = com.example.newsmvvm.databinding.MenuDialogBinding.bind(view)
        binding.categoryList.apply {
            adapter = dialogAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        dialog.setContentView(view)
        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
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

    override fun onDialogClick(item: DialogItem) {
        binding.tvCategory.text = item.title
    }

    override fun onCountryClick(country: DialogItem) {
        val countryCode = country.title
        val name = CountryCode.findByName(countryCode).get(0)
        binding.countryCode.text = name.toString()
    }

    override fun onArticleClick(article: Article) {
        val action =
            BreakingNewsFragmentDirections.actionBreakingNewsFragment2ToArticleFragment(article)
        findNavController().navigate(action)
    }
}