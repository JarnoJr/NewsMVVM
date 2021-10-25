package com.example.newsmvvm.framework.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmvvm.R
import com.example.newsmvvm.framework.adapter.FavouritesAdapter
import com.example.newsmvvm.databinding.FavouritesFragmentBinding
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.framework.presentation.viewmodel.FavouritesFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesFragment :
    BaseFragment<FavouritesFragmentBinding>(FavouritesFragmentBinding::inflate),
    FavouritesAdapter.OnItemClickListener {

    private val mViewModel: FavouritesFragmentViewModel by viewModels()

    @Inject
    lateinit var mAdapter: FavouritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter.setOnClickListener(this)
        initRecycler()
        swipeToDelete()
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

    override fun onArticleClick(article: Article) {
        val action =
            FavouritesFragmentDirections.actionFavouritesFragment2ToArticleFragment2(article)
        findNavController().navigate(action)
    }

    private fun swipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val article = mAdapter.differ.currentList[position]
                mViewModel.removeArticle(article)
                Snackbar.make(
                    requireContext(),
                    requireView(),
                    "Removed from  favs.",
                    Snackbar.LENGTH_LONG
                ).apply {
                    setActionTextColor(requireContext().resources.getColor(R.color.white))
                    setAction("Undo") {
                        mViewModel.saveArticle(article)
                    }
                    setBackgroundTint(requireContext().resources.getColor(R.color.success))
                }
                    .show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavouriteNews)
        }

    }
}