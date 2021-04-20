package com.alexcarbonell.rickandmorty.ui.main

import com.alexcarbonell.rickandmorty.databinding.ActivityMainBinding
import com.alexcarbonell.rickandmorty.ui.common.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
}
