package com.example.rickmortyreddit.model

interface Repository {
    suspend fun getCharacters(page: Int): RepositoryImpl.AppState
}