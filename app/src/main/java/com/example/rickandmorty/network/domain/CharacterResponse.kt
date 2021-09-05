package com.example.rickandmorty.network.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CharacterResponse(
    val info: Info,
    val results: List<ResultX>
)

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)

@Parcelize
data class ResultX(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
):Parcelable

@Parcelize
data class Location(
    val name: String,
    val url: String
):Parcelable

@Parcelize
data class Origin(
    val name: String,
    val url: String
):Parcelable