package com.example.rickmortyreddit.model.util

import com.example.rickmortyreddit.model.*
import com.example.rickmortyreddit.model.local.CharacterEntity

fun List<CharacterEntity>.mapToModel(): CharacterResponse?{
    if(this.isEmpty()) return null

    val characterResult = mutableListOf<CharacterResult>()
    for(characterEntity in this){
        characterResult.add(
            CharacterResult(
                name = characterEntity.name,
                location = CharacterLocation(
                    characterEntity.location
                ),
                origin = CharacterOrigin(
                    characterEntity.origin
                ),
                id = 0,
                status = "",
                species = "",
                gender = "",
                image = ""
            )
        )
    }
    return CharacterResponse(
        CharacterPage(0,0,"",""),
        characterResult
    )
}

fun CharacterResult.modelToEntity(): CharacterEntity{
    return CharacterEntity(
        this.name,
        this.location.name,
        this.origin.name
    )
}