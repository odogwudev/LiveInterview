package com.odogwudev.liveinterview.data.domain.rrom

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites ORDER BY name")
    fun observeAll(): Flow<List<CharacterRicks>>
    @Query("SELECT id FROM favorites")
    fun observeById(): Flow<List<Int>>
    @Upsert suspend fun upsertAll(items: List<CharacterRicks>)
    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteById(id: Int)
}
