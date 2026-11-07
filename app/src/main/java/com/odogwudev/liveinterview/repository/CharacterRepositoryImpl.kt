package com.odogwudev.liveinterview.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.odogwudev.liveinterview.data.domain.paging.CharacterPagingSource
import com.odogwudev.liveinterview.data.domain.rrom.CharacterRicks
import com.odogwudev.liveinterview.data.domain.rrom.FavoriteDao
import com.odogwudev.liveinterview.data.network.RickyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickyApi,
    private val dao: FavoriteDao
) : CharacterRepository {
    override fun character(): Flow<PagingData<CharacterRicks>> =
        Pager(PagingConfig(pageSize = 20)) {
            CharacterPagingSource(api)
        }.flow


    override val favorites: Flow<List<CharacterRicks>> = dao.observeAll()
    override val favoriteIds: Flow<Set<Int>> = dao.observeById().map { it.toSet() }

    override suspend fun addFavorites(items: List<CharacterRicks>) = dao.upsertAll(items)

    override suspend fun removeFavorite(id: Int) = dao.deleteById(id)

}