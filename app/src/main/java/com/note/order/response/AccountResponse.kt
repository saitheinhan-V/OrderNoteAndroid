package com.note.order.response

import com.note.order.request.LoginRequest
import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse(
    val statusCode: Int?=null,
    val token: String?=null,
    val user: LoginRequest?=null
)