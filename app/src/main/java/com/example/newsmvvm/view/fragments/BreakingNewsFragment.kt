package com.example.newsmvvm.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsmvvm.R
import com.example.newsmvvm.adapter.BreakingNewsAdapter
import com.example.newsmvvm.adapter.DialogAdapter
import com.example.newsmvvm.adapter.NewsLoadStateAdapter
import com.example.newsmvvm.adapter.OnItemClickListener
import com.example.newsmvvm.databinding.BreakingNewsFragmentBinding
import com.example.newsmvvm.model.Article
import com.example.newsmvvm.model.DialogItem
import com.example.newsmvvm.viewmodel.BreakingNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "BreakingNewsFragment"

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.breaking_news_fragment), OnItemClickListener,
    BreakingNewsAdapter.onItemClickListener {

    private val mViewModel by viewModels<BreakingNewsViewModel>()

    @Inject
    lateinit var mAdapter: BreakingNewsAdapter

    private var _mBinding: BreakingNewsFragmentBinding? = null

    private val mBinding get() = _mBinding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _mBinding = BreakingNewsFragmentBinding.bind(view)
        initRecycler()
        mAdapter.setOnClickListener(this)
        observeViewModel()
        if (savedInstanceState != null) {
            mBinding.countryCode.text = savedInstanceState.getString("country")
            mBinding.tvCategory.text = savedInstanceState.getString("category")
        }
        initToolbar()
        observeTextFields()
    }

    private fun initToolbar() {
        mBinding.toolbar.setOnMenuItemClickListener {
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
        mBinding.rvBreakingNews.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter { mAdapter.retry() },
                footer = NewsLoadStateAdapter { mAdapter.retry() }
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("category", mBinding.tvCategory.text.toString())
        outState.putString("country", mBinding.countryCode.text.toString())
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
        mBinding.tvCategory.addTextChangedListener {
            mViewModel.setCategory(it.toString().toLowerCase())
        }
        mBinding.countryCode.addTextChangedListener {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    override fun onDialogClick(item: DialogItem) {
        mBinding.tvCategory.text = item.title
    }

    override fun onCountryClick(country: DialogItem) {
        mBinding.countryCode.text = country.title
    }

    override fun onArticleClick(article: Article) {
        val action =
            BreakingNewsFragmentDirections.actionBreakingNewsFragment2ToArticleFragment(article)
        findNavController().navigate(action)
    }


}