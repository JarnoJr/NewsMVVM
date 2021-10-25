package com.example.newsmvvm.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class DialogAdapter<T>(
    private val inflate: (layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> ViewBinding,
    private val bind: (item: T, binding: ViewBinding) -> Unit,
    private val onClick: (item: T, position: Int) -> Unit,
    compareItems: (old: T, new: T) -> Boolean,
    compareContents: (old: T, new: T) -> Boolean
) : ListAdapter<T, RecyclerView.ViewHolder>(
    DiffCallback(
        compareItems,
        compareContents
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(inflate(LayoutInflater.from(parent.context), parent, false))
    }


    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bind(getItem(position), (holder as DialogAdapter<T>.ItemViewHolder).binding)
    }

    inner class ItemViewHolder(var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(
                    getItem(absoluteAdapterPosition),
                    absoluteAdapterPosition
                )
            }
        }
    }

    private class DiffCallback<K>(
        private val compareItems: (old: K, new: K) -> Boolean,
        private val compareContents: (old: K, new: K) -> Boolean
    ) : DiffUtil.ItemCallback<K>() {
        override fun areItemsTheSame(oldItem: K, newItem: K): Boolean =
            compareItems(oldItem, newItem)

        override fun areContentsTheSame(oldItem: K, newItem: K): Boolean =
            compareContents(oldItem, newItem)
    }
}
