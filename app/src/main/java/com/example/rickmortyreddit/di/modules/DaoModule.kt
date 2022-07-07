package com.example.rickmortyreddit.di.modules

import android.content.Context
import com.example.rickmortyreddit.model.local.RickMortyDb
import dagger.Module
import dagger.Provides

@Module
class DaoModule {
    @Provides
    fun provideDAO(context: Context) =
        RickMortyDb.initRoomDB(context).getDao()
}