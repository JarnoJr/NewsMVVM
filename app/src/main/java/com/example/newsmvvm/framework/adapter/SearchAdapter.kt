package com.example.newsmvvm.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.newsmvvm.R
import com.example.newsmvvm.business.domain.model.Article
import com.example.newsmvvm.business.domain.model.ArticleWithSeparator
import com.example.newsmvvm.databinding.NewsItemBinding
import com.example.newsmvvm.databinding.SeparatorItemBinding
import com.example.newsmvvm.util.ItemClickListener
import javax.inject.Inject

class SearchAdapter @Inject constructor(
    private val progressDrawable: CircularProgressDrawable
): PagingDataAdapter<ArticleWithSeparator, RecyclerView.ViewHolder>(
    ARTICLE_COMPARATOR) {

    private lateinit var listener:ItemClickListener<ArticleWithSeparator.ArticleItem>

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<ArticleWithSeparator>() {
            override fun areItemsTheSame(
                oldItem: ArticleWithSeparator,
                newItem: ArticleWithSeparator
            ): Boolean {
                return (oldItem is ArticleWithSeparator.ArticleItem && newItem is ArticleWithSeparator.ArticleItem &&
                        oldItem.article.url == newItem.article.url) ||
                        (oldItem is ArticleWithSeparator.Separator && newItem is ArticleWithSeparator.Separator &&
                                oldItem.query == newItem.query)
            }

            override fun areContentsTheSame(
                oldItem: ArticleWithSeparator,
                newItem: ArticleWithSeparator
            ): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            when (item) {
                is ArticleWithSeparator.ArticleItem -> (holder as BreakingNewsHolder).bind(item.article)
                is ArticleWithSeparator.Separator -> (holder as SeparatorViewHolder).bind(item.query)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.news_item) {
            val binding =
                NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BreakingNewsHolder(binding)
        } else {
            val binding =
                SeparatorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SeparatorViewHolder(binding)
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
                        listener.onClick(item as ArticleWithSeparator.ArticleItem)
                    }
                }

            }
        }

        fun bind(article: Article) {
            binding.article = article
            binding.progressDrawable = progressDrawable
        }
    }
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ArticleWithSeparator.ArticleItem -> R.layout.news_item
            is ArticleWithSeparator.Separator -> R.layout.separator_item
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    inner class SeparatorViewHolder(private val binding: SeparatorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.title = title
        }
    }

    fun setClickListener(listener:ItemClickListener<ArticleWithSeparator.ArticleItem>){
        this.listener = listener
    }
}