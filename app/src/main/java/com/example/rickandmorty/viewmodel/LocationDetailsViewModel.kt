package com.example.rickandmorty.viewmodel

import androidx.lifecycle.*
import com.example.rickandmorty.network.domain.NetworkException
import com.example.rickandmorty.network.domain.LocationDetailsResponseResult
import com.example.rickandmorty.network.domain.LocationResponse
import com.example.rickandmorty.repository.RickMortyRepository
import kotlinx.coroutines.launch

class LocationDetailsViewModel(private val repository: RickMortyRepository) : ViewModel() {

    private val _itemDetailsLiveData = MutableLiveData<LocationResponse>()
    val detailsLiveData: LiveData<LocationResponse> = _itemDetailsLiveData

    private val _errorLiveData = MutableLiveData<NetworkException>()
    val errorLiveData: LiveData<NetworkException> = _errorLiveData

    fun getItemDetails(id: Int?) {
        viewModelScope.launch {
            id?.let {
                repository.getLocationDetails(it).let { result ->
                    when (result) {
                        is LocationDetailsResponseResult.Success -> {
                            _itemDetailsLiveData.postValue(result.response)
                        }
                        is LocationDetailsResponseResult.Failure -> {
                            _errorLiveData.postValue(result.exception)
                        }
                    }
                }
            }
        }
    }

    class Factory(private val repository: RickMortyRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LocationDetailsViewModel(repository) as T
        }
    }
}