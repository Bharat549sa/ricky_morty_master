package com.example.rickmortyreddit.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

typealias Amount = Int
typealias Data = String
data class CharacterResponse(
    val info: CharacterPage,
    var results: List<CharacterResult>
)

data class CharacterPage(
    val count: Amount,
    val pages: Amount,
    val next: String?,
    val prev: String?
)
@Parcelize
data class CharacterResult(
    val id: Amount,
    val name: Data,
    val status: Data,
    val species: Data,
    val gender: Data,
    val origin: CharacterOrigin,
    val location: CharacterLocation,
    val image: Data
): Parcelable

@Parcelize
data class CharacterOrigin(
    val name: Data
): Parcelable

@Parcelize
data class CharacterLocation(
    val name: Data
): Parcelable