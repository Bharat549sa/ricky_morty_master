package com.example.rickmortyreddit.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class RickMortyDb: RoomDatabase() {
    abstract fun getDao(): RickMortyDao
    companion object{
        @Volatile
        private var INSTANCE: RickMortyDb? = null

        fun initRoomDB(context: Context): RickMortyDb{
            return INSTANCE?: synchronized(this){
                val temp = Room.databaseBuilder(
                    context,
                    RickMortyDb::class.java,
                    "rickmorty_db").build()
                INSTANCE = temp
                temp
            }
        }
    }
}