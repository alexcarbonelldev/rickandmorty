package com.alexcarbonell.domain.usecase

import com.alexcarbonell.domain.model.CharacterDomain
import com.alexcarbonell.domain.repository.RickAndMortyRepositoryContract
import com.alexcarbonell.domain.usecase.base.BaseUseCase

class GetCharactersUseCase(
    private val rickAndMortyRepository: RickAndMortyRepositoryContract
) : BaseUseCase<GetCharactersUseCase.Params, List<CharacterDomain>>() {

    override suspend fun func(params: Params): List<CharacterDomain> {
        return rickAndMortyRepository.getCharacters(params.page)
    }

    data class Params(val page: Int)
}
