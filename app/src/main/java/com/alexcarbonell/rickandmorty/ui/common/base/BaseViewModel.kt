package com.alexcarbonell.rickandmorty.ui.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexcarbonell.rickandmorty.ui.common.LiveEvent
import com.alexcarbonell.rickandmorty.ui.main.MainViewModel

abstract class BaseViewModel<VS, VE, VI> : ViewModel() {

    protected val _viewState = MutableLiveData<VS>()
    val viewState: LiveData<VS> = _viewState

    protected val _viewEffect = LiveEvent<VE>()
    val viewEffect: LiveData<VE> = _viewEffect

    abstract fun onViewIntent(viewIntent: MainViewModel.ViewIntent)
}
