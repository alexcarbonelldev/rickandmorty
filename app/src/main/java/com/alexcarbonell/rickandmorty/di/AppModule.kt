package com.alexcarbonell.rickandmorty.di

import com.alexcarbonell.rickandmorty.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MainViewModel(get()) }
}
