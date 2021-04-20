package com.alexcarbonell.data.remote.model

import com.google.gson.annotations.SerializedName

data class CharactersResponseApiDto(
    @SerializedName("results")
    val results: List<CharacterApiDto>?
)
