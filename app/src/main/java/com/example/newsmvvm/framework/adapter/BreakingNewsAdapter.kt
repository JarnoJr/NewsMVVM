package com.example.newsmvvm.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.databinding.NewsItemBinding
import com.example.newsmvvm.util.GlideUtil
import com.example.newsmvvm.util.ItemClickListener
import javax.inject.Inject


class BreakingNewsAdapter @Inject constructor(
    private val glideUtil: GlideUtil
) :
    PagingDataAdapter<Article, BreakingNewsAdapter.BreakingNewsHolder>(NEWS_COMPARATOR) {

    private lateinit var listener:ItemClickListener<Article>

    fun setOnClickListener(onClickListener: ItemClickListener<Article>) {
        this.listener = onClickListener
    }

    companion object {
        private val NEWS_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: BreakingNewsHolder, position: Int) {
        val currentArticle = getItem(position)
        currentArticle?.let {
            holder.bind(currentArticle)
        }
    }

    inner class BreakingNewsHolder(private val binding: NewsItemBinding) :
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreakingNewsHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreakingNewsHolder(binding)
    }
}

