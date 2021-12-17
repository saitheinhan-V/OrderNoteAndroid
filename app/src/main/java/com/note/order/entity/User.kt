package com.note.order.entity

import kotlinx.serialization.Serializable


data class User (
    val id: Int=0,
    val name: String="",
    val phone: String="",
    val avatar: String="",
    val token: String=""
)