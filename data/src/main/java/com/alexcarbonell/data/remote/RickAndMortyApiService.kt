package com.alexcarbonell.data.remote

import com.alexcarbonell.data.remote.model.CharactersResponseApiDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharactersResponseApiDto
}
