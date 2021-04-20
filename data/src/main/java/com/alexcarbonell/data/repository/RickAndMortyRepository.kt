package com.alexcarbonell.data.repository

import com.alexcarbonell.data.datasource.RickAndMortyApiDataSource
import com.alexcarbonell.data.mapper.toDomain
import com.alexcarbonell.domain.model.CharacterDomain
import com.alexcarbonell.domain.repository.RickAndMortyRepositoryContract

class RickAndMortyRepository(
    private val dataSource: RickAndMortyApiDataSource
) : RickAndMortyRepositoryContract {

    override suspend fun getCharacters(page: Int): List<CharacterDomain> {
        return dataSource.getCharacters(page).map { it.toDomain() }
    }
}
