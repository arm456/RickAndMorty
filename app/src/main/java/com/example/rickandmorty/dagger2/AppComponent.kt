package com.example.rickandmorty.dagger2

import android.content.Context
import com.example.rickandmorty.ui.LocationDetailsFragment
import com.example.rickandmorty.ui.CharactersFeedFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Component providing inject() methods.
 */
@Singleton
@Component(modules = [NetworkModule::class, AndroidInjectionModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(charactersFeedFragment: CharactersFeedFragment)

    fun inject(detailsFragment: LocationDetailsFragment)
}