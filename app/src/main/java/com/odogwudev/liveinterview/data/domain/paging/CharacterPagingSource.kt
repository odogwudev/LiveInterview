package com.odogwudev.liveinterview.data.domain.paging

import android.util.Log
import androidx.paging.LOG_TAG
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.odogwudev.liveinterview.data.domain.rrom.CharacterRicks
import com.odogwudev.liveinterview.data.domain.rrom.toDomain
import com.odogwudev.liveinterview.data.network.RickyApi
import kotlinx.coroutines.CancellationException

class CharacterPagingSource(private val api: RickyApi) : PagingSource<Int, CharacterRicks>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterRicks>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterRicks> {
        val page = params.key ?: 1
        return try {
            Log.d("R<", "loading page$page")
            val response = api.getCharacters(page)
            LoadResult.Page(
                data = response.results.map { it.toDomain() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.info.next != null) page + 1 else null
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(" Ricky pagomg", " load failed", e)
            LoadResult.Error(e)
        }
    }
}