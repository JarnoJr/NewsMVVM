package com.example.newsmvvm.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsmvvm.R
import com.example.newsmvvm.adapter.FavouritesAdapter
import com.example.newsmvvm.databinding.FavouritesFragmentBinding
import com.example.newsmvvm.model.Article
import com.example.newsmvvm.viewmodel.FavouritesFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.favourites_fragment),
    FavouritesAdapter.OnItemClickListener {

    private val mViewModel: FavouritesFragmentViewModel by viewModels()

    private var _binding: FavouritesFragmentBinding? = null

    private val binding: FavouritesFragmentBinding get() = _binding!!

    @Inject
    lateinit var mAdapter: FavouritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FavouritesFragmentBinding.bind(view)
        mAdapter.setOnClickListener(this)
        initRecycler()
        observeViewModel()
    }

    private fun initRecycler() {
        binding.rvFavouriteNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        mViewModel.getArticles().observe(viewLifecycleOwner) { news ->
            mAdapter.differ.submitList(news)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onArticleClick(article: Article) {
        val action =
            FavouritesFragmentDirections.actionFavouritesFragment2ToArticleFragment2(article)
        findNavController().navigate(action)
    }
}