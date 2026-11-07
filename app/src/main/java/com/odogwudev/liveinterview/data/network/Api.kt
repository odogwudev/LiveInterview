package com.odogwudev.liveinterview.data.network

import com.odogwudev.liveinterview.data.CharacterPage
import retrofit2.http.GET
import retrofit2.http.Query

interface RickyApi {
    @GET("character") suspend fun getCharacters(@Query("page") page: Int): CharacterPage
}