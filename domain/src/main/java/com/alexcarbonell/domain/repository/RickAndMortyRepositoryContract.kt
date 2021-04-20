package com.alexcarbonell.domain.repository

import com.alexcarbonell.domain.model.CharacterDomain

interface RickAndMortyRepositoryContract {

    suspend fun getCharacters(page: Int): List<CharacterDomain>
}
