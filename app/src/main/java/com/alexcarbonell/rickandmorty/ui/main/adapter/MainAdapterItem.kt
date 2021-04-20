package com.alexcarbonell.rickandmorty.ui.main.adapter

import com.alexcarbonell.domain.model.CharacterDomain

sealed class MainAdapterItem {
    abstract val id: Int

    data class Character(
        override val id: Int,
        val image: String?,
        val name: String?
    ) : MainAdapterItem()

    object LoadingFooter : MainAdapterItem() {
        override val id: Int = -1
    }
}

fun CharacterDomain.toMainAdapterItem(): MainAdapterItem {
    return MainAdapterItem.Character(
        id,
        image,
        name
    )
}
