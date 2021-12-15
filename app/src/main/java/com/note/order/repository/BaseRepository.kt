package com.note.order.repository

import android.app.Application
import com.note.order.APICall.KtorClient
import com.note.order.APICall.RequestBuilder
import com.note.order.APICall.Service
import com.note.order.UrlConfig
import com.note.order.request.LoginRequest
import com.note.order.response.AccountResponse
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.util.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseRepository(application: Application) {

    private val httpClient = KtorClient().createHttpClient()

    private val apiAuth = RequestBuilder.createService(Service::class.java, UrlConfig.baseUrl, "")


//    fun login(req: LoginRequest) = NetworkCall<JsonObject>().makeCall(apiAuth.login(req))

     suspend fun login(req: LoginRequest): AccountResponse? = withContext(Dispatchers.IO) {
        try {
            // Email Login
            val response = httpClient.post<AccountResponse>(UrlConfig.loginUrl) {
                body = req
                //header("X-Requested-With", "XMLHttpRequest")
            }
            response
        } catch (t: Throwable) {
            // Handle Error
            when (t) {
                is ClientRequestException ->
                    if (t.response?.status?.value == 403) {
                        // Invalid login credentials
                        null
                    } else {
                        // Unhandled error
                        null
                    }
                is UnresolvedAddressException -> {
                    // Internet Error
                    null
                }
                else -> {
                    // Unhandled error
                    null
                }
            }
        }
    }


}