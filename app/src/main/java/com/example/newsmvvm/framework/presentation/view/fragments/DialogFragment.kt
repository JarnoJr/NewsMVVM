package com.example.newsmvvm.framework.presentation.view.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsmvvm.R
import com.example.newsmvvm.framework.adapter.DialogAdapter
import com.example.newsmvvm.databinding.CategoryListItemBinding
import com.example.newsmvvm.databinding.MenuDialogBinding
import com.example.newsmvvm.business.domain.model.DialogItem
import com.neovisionaries.i18n.CountryCode

private const val TAG = "DialogFragment"
class DialogFragment : DialogFragment(R.layout.menu_dialog) {

    private var _binding: MenuDialogBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DialogFragmentArgs>()

    private lateinit var mAdapter: DialogAdapter<DialogItem>

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MenuDialogBinding.bind(view)
        initAdapter()
        initRecyclerview()
    }

    private fun initAdapter() {
        mAdapter = DialogAdapter(
            { layoutInflater, parent, attachToParent ->
                CategoryListItemBinding.inflate(layoutInflater, parent, attachToParent)
            },
            { item, binding ->
                run {
                    (binding as CategoryListItemBinding)
                    if (args.dialogItems.size == 7) {
                        val requestOptions = RequestOptions().placeholder(R.drawable.ic_category)
                        val path =
                            Uri.parse("android.resource://com.example.newsmvvm/drawable/" + item.imageUrl.toLowerCase())
                        Glide.with(requireContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(path)
                            .into(binding.ivCategory)
                        binding.tvCategory.text = item.title
                    } else {
                        val requestOptions = RequestOptions().placeholder(R.drawable.ic_country)
                        val url = "https://www.countryflags.io/" + item.imageUrl + "/flat/64.png"
                        Glide.with(requireContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(url)
                            .into(binding.ivCategory)
                        binding.tvCategory.text = item.title
                    }
                }
            },
            { item, _ ->
                run {
                    if (args.dialogItems.size == 7) {
                        findNavController().previousBackStackEntry?.savedStateHandle?.set(
                            "category",
                            item
                        )
                        dialog?.dismiss()
                    } else {
                        val countryCode = item.title
                        val name = CountryCode.findByName(countryCode)[0]
                        findNavController().previousBackStackEntry?.savedStateHandle?.set(
                            "country",
                            name.toString()
                        )
                        dialog?.dismiss()
                    }
                }
            },
            { old, new -> old.title == new.title },
            { old, new -> old == new }
        )
    }

    private fun initRecyclerview() {
        binding.categoryList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = mAdapter
        }
        mAdapter.submitList(args.dialogItems.toList())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}