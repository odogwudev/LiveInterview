package com.odogwudev.liveinterview.data.domain.rrom

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.odogwudev.liveinterview.data.network.CharacterDTO

@Entity(tableName = "favorites")
data class CharacterRicks (
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String,
  //  val episode: String,
    val url: String,
    val created: String
)

fun CharacterDTO.toDomain() = CharacterRicks(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.name,
    location = location.name,
    image = image,
   // episode = episode,
    url = url,
    created = created
)