package com.example.rickmortyreddit.model.remote

import com.example.rickmortyreddit.model.CharacterResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {
    @GET("api/character")
    suspend fun getCharacters(
        @Query("page")
        pageParam: Int = 1
    ): Response<CharacterResponse>

    companion object{
        fun getCharacterApi(): CharacterApi{
            return Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CharacterApi::class.java)
        }
    }
}