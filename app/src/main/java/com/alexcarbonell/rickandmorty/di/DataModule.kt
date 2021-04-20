package com.alexcarbonell.rickandmorty.di

import com.alexcarbonell.data.datasource.RickAndMortyApiDataSource
import com.alexcarbonell.data.remote.RetrofitClient
import com.alexcarbonell.data.repository.RickAndMortyRepository
import com.alexcarbonell.domain.repository.RickAndMortyRepositoryContract
import org.koin.dsl.module

val dataModule = module {

    factory<RickAndMortyRepositoryContract> { RickAndMortyRepository(get()) }
    factory { RickAndMortyApiDataSource(get()) }
    factory { RetrofitClient.RICK_AND_MORTY_API_SERVICE }
}
