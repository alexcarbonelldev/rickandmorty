package com.alexcarbonell.rickandmorty.ui.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.alexcarbonell.domain.exception.EndOfPaginationException
import com.alexcarbonell.domain.model.CharacterDomain
import com.alexcarbonell.domain.usecase.GetCharactersUseCase
import com.alexcarbonell.rickandmorty.ui.common.base.BaseViewModel
import com.alexcarbonell.rickandmorty.ui.main.adapter.MainAdapterItem
import com.alexcarbonell.rickandmorty.ui.main.adapter.toMainAdapterItem
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : BaseViewModel<MainViewModel.ViewState, MainViewModel.ViewEffect, MainViewModel.ViewIntent>() {

    override val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)

    private val charactersList = ArrayList<CharacterDomain>()

    private var charactersPage = 1
    private var charactersPageRequested = false
    private var charactersPageEndReached = false

    init {
        requestCharacters(isFirstRequest = true)
    }

    override fun onViewIntent(viewIntent: ViewIntent) {
        when (viewIntent) {
            ViewIntent.OnScrolledToBottom -> {
                requestCharacters()
            }
            is ViewIntent.OnCharacterClicked -> {
                charactersList.find { it.id == viewIntent.id }?.let { character ->
                    showEvent(ViewEffect.ShowSnackBar(character.name))
                }
            }
        }
    }

    private fun requestCharacters(isFirstRequest: Boolean = false) {
        if (!charactersPageRequested && !charactersPageEndReached) {
            charactersPageRequested = true

            if (isFirstRequest) {
                _viewState.value = ViewState.Loading
            } else {
                _viewState.value = ViewState.Success(
                    mapCharactersToAdapterItems(charactersList.toList(), true)
                )
            }

            getCharactersUseCase.execute(
                viewModelScope,
                GetCharactersUseCase.Params(charactersPage++),
                { onGetCharactersSuccess(it) },
                { onGetCharactersError(it) }
            )
        }
    }

    private fun onGetCharactersSuccess(result: List<CharacterDomain>) {
        charactersList.addAll(result)
        charactersPageRequested = false
        _viewState.value = ViewState.Success(
            mapCharactersToAdapterItems(charactersList.toList(), false)
        )
    }

    private fun onGetCharactersError(exception: Exception) {
        when (exception) {
            is EndOfPaginationException -> onPaginationEndReached()
            else -> exception.localizedMessage?.let { Log.e("APP_LOG", it) }
        }
    }

    private fun onPaginationEndReached() {
        charactersPageEndReached = true
        _viewState.value = ViewState.Success(
            mapCharactersToAdapterItems(charactersList.toList(), false)
        )
    }

    private fun mapCharactersToAdapterItems(
        characters: List<CharacterDomain>,
        isLoadingNewPage: Boolean
    ): List<MainAdapterItem> {
        val result = ArrayList<MainAdapterItem>()
        result.addAll(characters.map { it.toMainAdapterItem() })
        if (isLoadingNewPage) {
            result.add(MainAdapterItem.LoadingFooter)
        }
        return result
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Success(val characters: List<MainAdapterItem>) : ViewState()
        data class Error(val error: String) : ViewState()
    }

    sealed class ViewEffect {
        data class ShowSnackBar(val message: String) : ViewEffect()
    }

    sealed class ViewIntent {
        object OnScrolledToBottom : ViewIntent()
        data class OnCharacterClicked(val id: Int) : ViewIntent()
    }
}
