package com.example.newsmvvm.adapter

import android.app.Dialog
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsmvvm.R
import com.example.newsmvvm.databinding.CategoryListItemBinding
import com.example.newsmvvm.model.DialogItem
import com.example.newsmvvm.util.Consts
import com.neovisionaries.i18n.CountryCode

private const val TAG = "DialogAdapter"

class DialogAdapter(private val listener: OnItemClickListener, private val dialog: Dialog) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val CATEGORY_TYPE = 1
        const val COUNTRY_TYPE = 2
    }

    private val differcallback = object : DiffUtil.ItemCallback<DialogItem>() {
        override fun areItemsTheSame(oldItem: DialogItem, newItem: DialogItem): Boolean =
            oldItem.title == newItem.title


        override fun areContentsTheSame(oldItem: DialogItem, newItem: DialogItem): Boolean =
            oldItem == newItem
    }
    val diff = AsyncListDiffer(this, differcallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CATEGORY_TYPE -> CategoryViewHolder(
                CategoryListItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            COUNTRY_TYPE -> CountryViewHolder(
                CategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> CategoryViewHolder(
                CategoryListItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        val currentCategory = diff.currentList[position]
        if (itemViewType == CATEGORY_TYPE) {
            currentCategory?.let {
                (holder as CategoryViewHolder).bind(it)
            }
        } else if (itemViewType == COUNTRY_TYPE) {
            currentCategory?.let {
                (holder as CountryViewHolder).bind(it)
            }
        }
    }

    override fun getItemCount(): Int = diff.currentList.size

    inner class CountryViewHolder(private val binding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onCountryClick(diff.currentList[position])
                    dialog.dismiss()
                }
            }
        }

        fun bind(country: DialogItem) {
            val requestOptions = RequestOptions().placeholder(R.drawable.ic_country)
            val url = "https://www.countryflags.io/" + country.imageUrl + "/flat/64.png"
            Glide.with(itemView)
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .into(binding.ivCategory)
            binding.tvCategory.text = country.title

        }
    }

    inner class CategoryViewHolder(private val binding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDialogClick(diff.currentList[position])
                    dialog.dismiss()
                }
            }
        }

        fun bind(category: DialogItem) {
            val requestOptions = RequestOptions().placeholder(R.drawable.ic_category)
            val path =
                Uri.parse("android.resource://com.example.newsmvvm/drawable/" + diff.currentList[position].imageUrl.toLowerCase())
            Glide.with(itemView)
                .setDefaultRequestOptions(requestOptions)
                .load(path)
                .into(binding.ivCategory)
            binding.tvCategory.text = category.title
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (diff.currentList.size > 7) {
            COUNTRY_TYPE
        } else {
            CATEGORY_TYPE
        }
    }

    fun displayCategories() {
        val categories = mutableListOf<DialogItem>()
        for (i in Consts.CATEGORIES.indices) {
            val category = DialogItem()
            category.title = Consts.CATEGORIES[i]
            category.imageUrl = Consts.CATEGORIES[i]
            categories.add(category)
        }
        diff.submitList(categories)
    }

    fun displayCountries() {
        val countries = mutableListOf<DialogItem>()
        for (i in Consts.COUNTRIES.indices) {
            val country = DialogItem()
            val cc = CountryCode.getByAlpha2Code(Consts.COUNTRIES[i].toUpperCase())
            val countryName = cc.getName()
            Log.i(TAG, "displayCountries: $countryName")
            country.title = countryName
            country.imageUrl = cc.toString().toLowerCase()
            countries.add(country)
        }
        diff.submitList(countries)
    }
}

interface OnItemClickListener {
    fun onDialogClick(category: DialogItem)
    fun onCountryClick(country: DialogItem)
}
