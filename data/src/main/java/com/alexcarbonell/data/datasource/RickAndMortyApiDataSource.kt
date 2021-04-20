package com.alexcarbonell.data.datasource

import com.alexcarbonell.data.datasource.model.CharacterData
import com.alexcarbonell.data.mapper.toData
import com.alexcarbonell.data.remote.ApiExceptionFactory
import com.alexcarbonell.data.remote.RickAndMortyApiService
import retrofit2.HttpException

class RickAndMortyApiDataSource(
    private val apiService: RickAndMortyApiService
) {

    suspend fun getCharacters(page: Int): List<CharacterData> {
        try {
            val response = apiService.getCharacters(page)
            return response.results?.map { it.toData() } ?: throw Exception()
        } catch (e: HttpException) {
            throw ApiExceptionFactory.create(e)
        }
    }
}
