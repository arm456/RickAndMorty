package com.example.rickandmorty.repository

import com.example.rickandmorty.network.api.RickMortyAPI
import com.example.rickandmorty.network.domain.NetworkException
import com.example.rickandmorty.network.domain.LocationDetailsResponseResult
import com.example.rickandmorty.network.domain.CharactersFeedResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

interface RickMortyRepository {
    suspend fun getCharactersFeed(): CharactersFeedResponseResult

    suspend fun getLocationDetails(id: Int): LocationDetailsResponseResult
}

class RickMortyRepositoryImpl @Inject constructor(private val rickMortyAPI: RickMortyAPI) :
    RickMortyRepository {

    override suspend fun getCharactersFeed(): CharactersFeedResponseResult {
        return try {
            withContext(Dispatchers.IO) {
                val response =
                    rickMortyAPI.getCharactersFeed().execute()
                if (response.isSuccessful && response.body() != null) {
                    CharactersFeedResponseResult.Success(response.body())
                } else {
                    CharactersFeedResponseResult.Failure(
                        NetworkException(response.errorBody()?.string())
                    )
                }
            }
        } catch (e: Exception) {
            CharactersFeedResponseResult.Failure(exception = NetworkException(e.message))
        }
    }

    override suspend fun getLocationDetails(id: Int): LocationDetailsResponseResult {
        return try {
            withContext(Dispatchers.IO) {
                val locationDetailsResponse =
                    rickMortyAPI.getLocationDetails(id).execute()
                if (locationDetailsResponse.isSuccessful && locationDetailsResponse.body() != null) {
                    LocationDetailsResponseResult.Success(locationDetailsResponse.body())
                } else {
                    LocationDetailsResponseResult.Failure(
                        NetworkException(
                            locationDetailsResponse.errorBody()?.string()
                        )
                    )
                }
            }
        } catch (e: Exception) {
            LocationDetailsResponseResult.Failure(exception = NetworkException(e.message))
        }
    }

}