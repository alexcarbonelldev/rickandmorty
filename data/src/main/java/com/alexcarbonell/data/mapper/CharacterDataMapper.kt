package com.alexcarbonell.data.mapper

import com.alexcarbonell.data.datasource.model.CharacterData
import com.alexcarbonell.data.remote.model.CharacterApiDto
import com.alexcarbonell.domain.model.CharacterDomain

fun CharacterApiDto.toData(): CharacterData {
    return CharacterData(
        id ?: 0,
        name ?: "",
        image ?: ""
    )
}

fun CharacterData.toDomain(): CharacterDomain {
    return CharacterDomain(
        id,
        name,
        image
    )
}
