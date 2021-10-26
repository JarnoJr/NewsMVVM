package com.example.newsmvvm.framework.presentation.view.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.newsmvvm.R
import com.example.newsmvvm.business.domain.model.DialogItem
import com.example.newsmvvm.databinding.DialogFragmentBinding
import com.example.newsmvvm.framework.adapter.DialogAdapter
import com.example.newsmvvm.util.Constants.CATEGORY_KEY
import com.example.newsmvvm.util.Constants.COUNTRY_KEY
import com.example.newsmvvm.util.ItemClickListener
import com.example.newsmvvm.util.setNavigationResult

class DialogFragment : DialogFragment(R.layout.dialog_fragment), ItemClickListener<DialogItem> {

    private lateinit var binding: DialogFragmentBinding

    private val args by navArgs<DialogFragmentArgs>()

    private lateinit var mAdapter: DialogAdapter

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogFragmentBinding.bind(view)
        initRecyclerview()
    }

    private fun initRecyclerview() {
        mAdapter = DialogAdapter(this)
        binding.adapter = mAdapter
        mAdapter.submitList(args.items.toList())
    }

    override fun onClick(item: DialogItem) {
        val key = if (args.items.size == 7) {
            CATEGORY_KEY
        } else {
            COUNTRY_KEY
        }
        setNavigationResult(key, item)
        dialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}