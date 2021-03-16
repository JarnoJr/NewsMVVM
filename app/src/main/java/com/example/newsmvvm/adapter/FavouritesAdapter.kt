package com.example.newsmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsmvvm.R
import com.example.newsmvvm.databinding.NewsItemBinding
import com.example.newsmvvm.model.Article
import com.example.newsmvvm.util.GlideUtil
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class FavouritesAdapter @Inject constructor(
    private val glideUtil: GlideUtil
) :
    RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    private lateinit var onClickListener: OnItemClickListener

    fun setOnClickListener(onClickListener: OnItemClickListener) {
        this.onClickListener = onClickListener
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouritesAdapter.ViewHolder {
        return ViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavouritesAdapter.ViewHolder, position: Int) {
        val currentArticle = differ.currentList[position]
        holder.bind(currentArticle)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = differ.currentList[position]
                    if (item != null) {
                        onClickListener.onArticleClick(item)
                    }
                }
            }
        }

        fun bind(article: Article) {
            binding.apply {
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .error(R.drawable.ic_error)
                    .placeholder(glideUtil.progressUtil(itemView.context))
                    .into(ivNews)

                tvDate.text = article.publishedAt
                tvAuthor.text = article.author
                tvNewsTitle.text = article.title
            }
        }
    }

    interface OnItemClickListener {
        fun onArticleClick(article: Article)
    }
}