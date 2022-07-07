package com.example.rickmortyreddit

import android.app.Application
import com.example.rickmortyreddit.di.component.DaggerRickComponent
import com.example.rickmortyreddit.di.component.RickComponent
import com.example.rickmortyreddit.di.modules.RepositoryModule

class RickApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerRickComponent.builder()
            .repositoryModule(RepositoryModule())
            .build()
    }

    companion object{
        lateinit var component: RickComponent
    }
}