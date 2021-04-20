package com.alexcarbonell.rickandmorty.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.alexcarbonell.rickandmorty.databinding.CharacterListItemBinding
import com.alexcarbonell.rickandmorty.databinding.LoadingFooterListItemBinding
import com.alexcarbonell.rickandmorty.ui.common.extensions.loadImage

class MainListAdapter(
    private val onBindLoadingFooter: (position: Int) -> Unit,
    private val onCharacterClicked: (id: Int) -> Unit
) : ListAdapter<MainAdapterItem, MainListAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_LOADING_FOOTER -> {
                val viewBinding = LoadingFooterListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ViewHolder.LoadingFooterViewHolder(viewBinding)
            }
            else -> {
                val viewBinding = CharacterListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ViewHolder.CharacterViewHolder(viewBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.CharacterViewHolder -> {
                (getItem(position) as? MainAdapterItem.Character)?.let {
                    holder.bind(it, onCharacterClicked)
                }
            }
            is ViewHolder.LoadingFooterViewHolder -> {
                holder.bind { onBindLoadingFooter(position) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MainAdapterItem.Character -> ITEM_VIEW_TYPE_ITEM
            MainAdapterItem.LoadingFooter -> ITEM_VIEW_TYPE_LOADING_FOOTER
        }
    }

    sealed class ViewHolder(viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        class CharacterViewHolder(
            private val viewBinding: CharacterListItemBinding
        ) : ViewHolder(viewBinding) {

            fun bind(item: MainAdapterItem.Character, onCharacterClicked: (id: Int) -> Unit) =
                with(viewBinding) {
                    this.root.setOnClickListener {
                        onCharacterClicked(item.id)
                    }
                    nameTextView.text = item.name
                    item.image?.let { imageView.loadImage(it, true, 24) }
                }
        }

        class LoadingFooterViewHolder(
            viewBinding: LoadingFooterListItemBinding
        ) : ViewHolder(viewBinding) {

            fun bind(onBind: () -> Unit) {
                onBind()
            }
        }
    }

    companion object {

        const val ITEM_VIEW_TYPE_LOADING_FOOTER = 0
        const val ITEM_VIEW_TYPE_ITEM = 1

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<MainAdapterItem>() {

            override fun areItemsTheSame(
                oldItem: MainAdapterItem,
                newItem: MainAdapterItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MainAdapterItem,
                newItem: MainAdapterItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
