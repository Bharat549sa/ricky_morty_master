package com.example.rickmortyreddit.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rick_morty")
data class CharacterEntity(
    @ColumnInfo(name="Name")
    @PrimaryKey
    val name: String,
    @ColumnInfo(name="Location")
    val location: String,
    @ColumnInfo(name = "Origin")
    val origin: String
)