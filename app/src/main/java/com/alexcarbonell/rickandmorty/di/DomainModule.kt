package com.alexcarbonell.rickandmorty.di

import com.alexcarbonell.domain.usecase.GetCharactersUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetCharactersUseCase(get()) }
}
