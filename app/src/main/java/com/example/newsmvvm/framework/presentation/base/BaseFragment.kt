package com.example.newsmvvm.framework.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.newsmvvm.framework.presentation.viewmodel.MainActivityViewModel

abstract class BaseFragment<DB : ViewDataBinding, VM : ViewModel> : Fragment() {

    lateinit var binding: DB

    private lateinit var viewModel: VM

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun getVM(): VM

    private val activityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getVM()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun launchOnLifecycleScope(execute: suspend () -> Unit) =
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            execute()
        }


    fun showProgress(show: Boolean) {
        activityViewModel.setShowProgress(show)
    }

    abstract fun init()

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
        showProgress(false)
    }

}