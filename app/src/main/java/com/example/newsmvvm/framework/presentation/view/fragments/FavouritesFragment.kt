package com.example.newsmvvm.framework.presentation.view.fragments

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmvvm.R
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.databinding.FavouritesFragmentBinding
import com.example.newsmvvm.framework.adapter.FavouritesAdapter
import com.example.newsmvvm.framework.presentation.base.BaseFragment
import com.example.newsmvvm.framework.presentation.viewmodel.FavouritesFragmentViewModel
import com.example.newsmvvm.util.ItemClickListener
import com.example.newsmvvm.util.showSnack
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "FavouritesFragment"

@AndroidEntryPoint
class FavouritesFragment :
    BaseFragment<FavouritesFragmentBinding, FavouritesFragmentViewModel>(),
    ItemClickListener<Article> {

    private val mViewModel: FavouritesFragmentViewModel by viewModels()

    @Inject
    lateinit var mAdapter: FavouritesAdapter

    override fun init() {
        mAdapter.setListener(this)
        binding.adapter = mAdapter
        swipeToDelete()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        mViewModel.favorites.observe(viewLifecycleOwner) {
            showEmptyView(it.isEmpty())
            mAdapter.submitList(it)
        }
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
                val article = mAdapter.currentList[position]
                mViewModel.removeArticle(article)
                showSnack(
                    "Removed From Favorites",
                    callback = { mViewModel.insertArticle(article) })
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.favoritesRv)
        }
    }

    private fun showEmptyView(show: Boolean) {
        binding.show = show
    }

    override val layoutId: Int
        get() = R.layout.favourites_fragment

    override fun getVM(): FavouritesFragmentViewModel = mViewModel

    override fun onClick(item: Article) {
        val action = FavouritesFragmentDirections.actionFavouritesFragmentToArticleFragment(item)
        findNavController().navigate(action)
    }
}