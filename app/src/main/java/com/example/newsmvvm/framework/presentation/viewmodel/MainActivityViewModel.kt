package com.example.newsmvvm.framework.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainActivityViewModel:ViewModel() {

    private val _showProgress = Channel<Boolean>()

    val showProgress = _showProgress.receiveAsFlow()


    fun setShowProgress(show:Boolean){
        viewModelScope.launch {
            _showProgress.send(show)
        }
    }
}