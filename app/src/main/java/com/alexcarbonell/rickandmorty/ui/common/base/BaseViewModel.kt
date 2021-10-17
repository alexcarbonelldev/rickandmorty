package com.alexcarbonell.rickandmorty.ui.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<VS, VE, VI> : ViewModel() {

    protected abstract val _viewState: MutableStateFlow<VS>
    val viewState: StateFlow<VS> by lazy { _viewState }

    protected val _viewEffect = Channel<VE>()
    val viewEffect: Flow<VE> = _viewEffect.receiveAsFlow()

    abstract fun onViewIntent(viewIntent: VI)

    protected fun showEvent(event: VE) {
        viewModelScope.launch {
            _viewEffect.send(event)
        }
    }
}
