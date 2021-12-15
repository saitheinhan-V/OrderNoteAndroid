package com.note.order.APICall

import android.text.TextUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestBuilder {

    private lateinit var retrofit: Retrofit
    private lateinit var builder: Retrofit.Builder

    fun <S> createService(serviceClass: Class<S>, baseUrl: String): S {
        return createService(serviceClass, baseUrl, "")
    }

    fun <S> createService(serviceClass: Class<S>, baseUrl: String, authToken: String?): S {
        builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())

        retrofit = builder.build()

        if (!TextUtils.isEmpty(authToken)){
            val interceptor = AuthenticationInterceptor(authToken!!)
            val client = OkHttpClient.Builder()
            if (!client.interceptors().contains(interceptor)) {
                client.addInterceptor(interceptor)
                builder.client(client.build())
            }
            retrofit = builder.build()
        }
        return retrofit.create(serviceClass)
    }
}