package com.alexcarbonell.rickandmorty.ui.common.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun <T> Fragment.observe(liveData: LiveData<T>, callback: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, callback)
}
