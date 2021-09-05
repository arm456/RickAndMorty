package com.example.rickandmorty.network.api

import com.example.rickandmorty.network.domain.CharacterResponse
import com.example.rickandmorty.network.domain.LocationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RickMortyAPI {

    @GET("api/character")
    fun getCharactersFeed(): Call<CharacterResponse>

    @GET("api/location/{id}")
    fun getLocationDetails(
        @Path("id") id: Int
    ): Call<LocationResponse>
}