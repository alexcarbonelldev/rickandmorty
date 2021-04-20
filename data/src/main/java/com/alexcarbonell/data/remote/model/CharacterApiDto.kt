package com.alexcarbonell.data.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterApiDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image")
    val image: String?
)
