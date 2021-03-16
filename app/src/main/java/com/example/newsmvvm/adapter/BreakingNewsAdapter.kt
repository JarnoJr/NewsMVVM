package com.example.newsmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsmvvm.R
import com.example.newsmvvm.databinding.NewsItemBinding
import com.example.newsmvvm.model.Article
import com.example.newsmvvm.util.GlideUtil
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class BreakingNewsAdapter @Inject constructor(
    private val glideUtil: GlideUtil
) :
    PagingDataAdapter<Article, BreakingNewsAdapter.BreakingNewsHolder>(NEWS_COMPARATOR) {


    private lateinit var listener: onItemClickListener

    fun setOnClickListener(onClickListener: onItemClickListener) {
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
                        listener.onArticleClick(item)
                    }
                }

            }
        }

        fun bind(article: Article) {
            binding.apply {
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .centerCrop()
                    .placeholder(glideUtil.progressUtil(itemView.context))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivNews)

                tvNewsTitle.text = article.title
                tvAuthor.text = article.author
                tvDate.text = article.publishedAt

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreakingNewsHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreakingNewsHolder(binding)
    }

    interface onItemClickListener {
        fun onArticleClick(article: Article)
    }
}

