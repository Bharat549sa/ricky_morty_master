package com.example.rickmortyreddit.model.local

import androidx.room.*

@Dao
interface RickMortyDao {
    // read cache, clean cache, insert cache

    @Insert(entity = CharacterEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFromRemote(item: CharacterEntity)

    @Query("DELETE FROM RICK_MORTY")
    suspend fun cleanCache()

    @Query("SELECT * FROM RICK_MORTY")
    suspend fun readCache(): List<CharacterEntity>
}