package com.odogwudev.liveinterview.data

import kotlinx.serialization.Serializable

@Serializable
data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String? =null,
    val prev: String? =null,
)