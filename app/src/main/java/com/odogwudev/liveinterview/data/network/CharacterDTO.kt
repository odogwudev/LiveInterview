package com.odogwudev.liveinterview.data.network

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDTO (
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: NamedRef,
    val location: NamedRef,
    val image: String,
    val url: String,
    val created: String
)
@Serializable
data class NamedRef(
    val name: String
)
