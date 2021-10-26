package com.example.newsmvvm.framework.presentation.view.fragments

import androidx.appcompat.widget.SearchView
import com.example.newsmvvm.business.domain.model.Article

//@AndroidEntryPoint
//class SearchFragment : BaseFragment<SearchNewsFragmentBinding>(SearchNewsFragmentBinding::inflate),
//    OnArticleClickListener {
//
//
//    private val mViewModel: SearchFragmentViewModel by viewModels()
//
//    @Inject
//    lateinit var mAdapter: BreakingNewsAdapter
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initRecycler()
//        initAdapter()
//        initSearchView()
//        initChips()
//        observeViewModel()
//        binding.loadingOrErrorLayout.buttonRetry.setOnClickListener {
//            mAdapter.retry()
//        }
//    }
//
//    private fun initRecycler() {
//        binding.rvSearchNews.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            setHasFixedSize(true)
//            adapter = mAdapter
//        }
//    }

//private fun initAdapter() {
//        mAdapter.setOnClickListener(this)
//        mAdapter.addLoadStateListener { loadState ->
//            binding.apply {
//                loadingOrErrorLayout.progressBar.isVisible =
//                    loadState.source.refresh is LoadState.Loading
//                rvSearchNews.isVisible = loadState.source.refresh is LoadState.NotLoading
//                loadingOrErrorLayout.buttonRetry.isVisible =
//                    loadState.source.refresh is LoadState.Error
//                loadingOrErrorLayout.textViewError.isVisible =
//                    loadState.source.refresh is LoadState.Error
//
//                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && mAdapter.itemCount < 1) {
//                    rvSearchNews.isVisible = false
//                    loadingOrErrorLayout.textViewEmpty.isVisible = true
//                } else {
//                    loadingOrErrorLayout.textViewEmpty.isVisible = false
//                }
//            }
//        }
//}
//
//private fun initSearchView() {
//    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//        override fun onQueryTextSubmit(query: String?): Boolean {
//            if (query != null) {
//                binding.rvSearchNews.scrollToPosition(0)
////                    mViewModel.setQuery(query)
//                binding.searchView.clearFocus()
//            }
//            return true
//        }
//
//        override fun onQueryTextChange(newText: String?): Boolean {
//            newText?.let {
//                if (it.isEmpty()) {
////                        mViewModel.setQuery("android")
//                }
//            }
//            return true
//        }
//    })
//}
//
//private fun initChips() {
//    binding.sortChipGroup.setOnCheckedChangeListener { _, checkedId ->
//        when (checkedId) {
////                R.id.relevancy -> mViewModel.setChip(SortBy.RELEVANCY.sortBy)
////                R.id.popularity -> mViewModel.setChip(SortBy.POPULARITY.sortBy)
////                R.id.publishedAt -> mViewModel.setChip(SortBy.PUBLISHEDAT.sortBy)
//        }
//    }
//}
//
//private fun observeViewModel() {
//    lifecycleScope.launchWhenStarted {
////            mViewModel.news.observe(viewLifecycleOwner) { news ->
////                mAdapter.submitData(viewLifecycleOwner.lifecycle, news)
////            }
//    }
//}
//
//override fun onArticleClick(article: Article) {
////        val action = SearchFragmentDirections.actionSearchFragmentToArticleFragment3(article)
////        findNavController().navigate(action)
//}
//}