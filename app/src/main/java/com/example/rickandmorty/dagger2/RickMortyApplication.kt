package com.example.rickandmorty.dagger2

import android.app.Application

class RickMortyApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}