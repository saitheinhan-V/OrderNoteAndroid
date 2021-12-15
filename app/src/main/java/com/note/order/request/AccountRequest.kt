package com.note.order.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("id")
    val id: String = "",
    @SerialName("phone")
    val phone: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("age")
    val age: String = ""
)