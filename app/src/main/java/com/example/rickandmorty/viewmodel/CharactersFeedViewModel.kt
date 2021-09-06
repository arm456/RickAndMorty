package com.example.rickandmorty.viewmodel

import androidx.lifecycle.*
import com.example.rickandmorty.network.domain.NetworkException
import com.example.rickandmorty.network.domain.ResultX
import com.example.rickandmorty.network.domain.CharactersFeedResponseResult
import com.example.rickandmorty.repository.RickMortyRepository
import com.example.rickandmorty.ui.OnClickEvent
import kotlinx.coroutines.launch

class CharactersFeedViewModel(private val repository: RickMortyRepository) : ViewModel() {

    private val _charactersLiveData = MutableLiveData<MutableList<ResultX>>()
    val charactersLiveData: LiveData<MutableList<ResultX>> = _charactersLiveData

    private val _characterItemClickLiveData = MutableLiveData<OnClickEvent<ResultX>>()
    val characterItemClickLiveData: LiveData<OnClickEvent<ResultX>> = _characterItemClickLiveData

    private val _errorLiveData = MutableLiveData<NetworkException>()
    val errorLiveData: LiveData<NetworkException> = _errorLiveData

    fun getCharactersResponse() {
        viewModelScope.launch {
            repository.getCharactersFeed()
                .let { result ->
                    when (result) {
                        is CharactersFeedResponseResult.Success -> {
                            _charactersLiveData.postValue(
                                _charactersLiveData.value.apply {
                                    result.response?.results?.let {
                                        this?.addAll(it)
                                    }
                                } ?: run {
                                    result.response?.results?.toMutableList()
                                }
                            )
                        }
                        is CharactersFeedResponseResult.Failure -> {
                            _errorLiveData.postValue(result.exception)
                        }
                    }
                }
        }
    }

    fun onListItemClicked(id: ResultX) {
        _characterItemClickLiveData.postValue(OnClickEvent(id))
    }

    class Factory(private val repository: RickMortyRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharactersFeedViewModel(repository) as T
        }
    }
}