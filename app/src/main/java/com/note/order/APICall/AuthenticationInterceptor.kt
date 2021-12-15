package com.note.order.APICall

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(token: String) : Interceptor {

    private var authToken: String? = token

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()
            .addHeader("Authorization", "Bearer " + authToken!!)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")

        val request = builder.build()
        return chain.proceed(request)
    }

}