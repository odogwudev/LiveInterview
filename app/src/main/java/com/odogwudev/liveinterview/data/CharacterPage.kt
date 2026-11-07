package com.odogwudev.liveinterview.data

import com.odogwudev.liveinterview.data.network.CharacterDTO
import kotlinx.serialization.Serializable

@Serializable
data class CharacterPage (
    val info: PageInfo,
    val results: List<CharacterDTO>
)
