package com.alexcarbonell.rickandmorty.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexcarbonell.rickandmorty.databinding.FragmentMainBinding
import com.alexcarbonell.rickandmorty.ui.common.base.BaseFragment
import com.alexcarbonell.rickandmorty.ui.common.extensions.onScrolledToBottom
import com.alexcarbonell.rickandmorty.ui.main.adapter.MainListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel: MainViewModel by viewModel()

    private val characterListAdapter = MainListAdapter(
        { position ->
            binding.recyclerView.smoothScrollToPosition(position)
        },
        { id ->
            viewModel.onViewIntent(MainViewModel.ViewIntent.OnCharacterClicked(id))
        }
    )

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ) = FragmentMainBinding.inflate(inflater, parent, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.recyclerView.adapter = characterListAdapter
        binding.recyclerView.onScrolledToBottom {
            viewModel.onViewIntent(MainViewModel.ViewIntent.OnScrolledToBottom)
        }
        (binding.recyclerView.layoutManager as? GridLayoutManager)?.let { layoutManager ->
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (characterListAdapter.getItemViewType(position)) {
                        MainListAdapter.ITEM_VIEW_TYPE_LOADING_FOOTER -> 2
                        else -> 1
                    }
                }
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect {
                setViewState(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.viewEffect.collect {
                showViewEffect(it)
            }
        }
    }

    private fun setViewState(viewState: MainViewModel.ViewState) {
        when (viewState) {
            MainViewModel.ViewState.Loading -> {
                binding.progressView.visibility = View.VISIBLE
            }
            is MainViewModel.ViewState.Success -> {
                binding.progressView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                characterListAdapter.submitList(viewState.characters)
            }
            is MainViewModel.ViewState.Error -> Unit
        }
    }

    private fun showViewEffect(viewEffect: MainViewModel.ViewEffect) {
        when (viewEffect) {
            is MainViewModel.ViewEffect.ShowSnackBar -> {
                Snackbar.make(binding.root, viewEffect.message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
