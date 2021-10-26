package com.example.newsmvvm.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmvvm.business.domain.model.DialogItem
import com.example.newsmvvm.databinding.DialogItemBinding
import com.example.newsmvvm.util.ItemClickListener

class DialogAdapter(private val listener: ItemClickListener<DialogItem>) :
    ListAdapter<DialogItem, DialogAdapter.ItemViewHolder>(ITEM_COMPARATOR) {


    companion object {
        val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<DialogItem>() {
            override fun areItemsTheSame(oldItem: DialogItem, newItem: DialogItem): Boolean =
                oldItem.text == newItem.text

            override fun areContentsTheSame(oldItem: DialogItem, newItem: DialogItem): Boolean =
                oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            DialogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ItemViewHolder(var binding: DialogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    item?.let {
                        listener.onClick(it)
                    }
                }
            }
        }
        fun bind(item: DialogItem) {
            binding.item = item
        }
    }


}
