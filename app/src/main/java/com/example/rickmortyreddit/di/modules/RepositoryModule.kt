package com.example.rickmortyreddit.di.modules

import android.content.Context
import com.example.rickmortyreddit.model.Repository
import com.example.rickmortyreddit.model.RepositoryImpl
import com.example.rickmortyreddit.model.local.RickMortyDao
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideRepository(context: Context, dao: RickMortyDao): Repository =
        RepositoryImpl(context, dao)
}