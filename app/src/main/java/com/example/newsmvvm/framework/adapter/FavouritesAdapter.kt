package com.example.newsmvvm.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.databinding.NewsItemBinding
import com.example.newsmvvm.util.ItemClickListener
import javax.inject.Inject

class FavouritesAdapter @Inject constructor(
    private val progressDrawable: CircularProgressDrawable
) : ListAdapter<Article, RecyclerView.ViewHolder>(differCallback) {

    private lateinit var listener: ItemClickListener<Article>

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as ViewHolder).bind(it)
        }
    }

    inner class ViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onClick(item)
                    }
                }
            }
        }

        fun bind(article: Article) {
            binding.article = article
            binding.progressDrawable = progressDrawable
        }
    }

    fun setListener(listener: ItemClickListener<Article>) {
        this.listener = listener
    }
}