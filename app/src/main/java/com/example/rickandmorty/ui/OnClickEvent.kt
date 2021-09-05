package com.example.rickandmorty.ui

import com.example.rickandmorty.network.domain.ResultX

class OnClickEvent<T>(private val id: Int) {
    
    var receivedData = false
        private set

    fun getDataIfReceived(): Int? {
        return if (receivedData) {
            null
        } else {
            receivedData = true
            id
        }
    }
}