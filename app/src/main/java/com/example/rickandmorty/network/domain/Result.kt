package com.example.rickandmorty.network.domain

/**
 * Sealed class for Success, failure & Loading result
 */
sealed class CharactersFeedResponseResult {
    data class Success(val response: CharacterResponse?) : CharactersFeedResponseResult()
    data class Failure(val exception: NetworkException) : CharactersFeedResponseResult()
}

/**
 * Sealed class for Success, failure & Loading result
 */
sealed class LocationDetailsResponseResult {
    data class Success(val response: LocationResponse?) : LocationDetailsResponseResult()
    data class Failure(val exception: NetworkException) : LocationDetailsResponseResult()
}

data class NetworkException(
    val errorMessage: String?
) : Exception()