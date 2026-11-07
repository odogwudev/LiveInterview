package com.odogwudev.liveinterview.repository

import androidx.paging.PagingData
import com.odogwudev.liveinterview.data.domain.rrom.CharacterRicks
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun character() : Flow<PagingData<CharacterRicks>>
    val favorites: Flow<List<CharacterRicks>>
    val favoriteIds: Flow<Set<Int>>
    suspend fun addFavorites(items: List<CharacterRicks>)
    suspend fun removeFavorite(id: Int)
}