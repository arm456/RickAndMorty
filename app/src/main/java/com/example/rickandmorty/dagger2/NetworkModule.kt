package com.example.rickandmorty.dagger2

import com.example.rickandmorty.network.api.RickMortyAPI
import com.example.rickandmorty.repository.RickMortyRepository
import com.example.rickandmorty.repository.RickMortyRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun rickMortyRepository(service: RickMortyAPI): RickMortyRepository =
        RickMortyRepositoryImpl(service)


    @Provides
    internal fun getRickyMortyAPI(retrofit: Retrofit): RickMortyAPI {
        return retrofit.create(RickMortyAPI::class.java)
    }

    @Provides
    internal fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    internal fun getOkHttClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/"
    }
}