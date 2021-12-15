package com.note.order.APICall

import com.google.gson.JsonObject
import com.note.order.entity.BrandItem
import com.note.order.request.BrandRequest
import com.note.order.request.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @GET("/get_brand.php")
    fun getAllBrand(): Call<MutableList<BrandItem>>

    @POST("/save_brand.php")
    fun saveBrand(
        @Body item : BrandRequest
    ): Call<JsonObject>

//    suspend fun login(req: LoginRequest) {
//        client.post<String>(UrlConfig().loginUrl) {
//            body = req
//        }
//    }

    @POST("/login")
    fun login(
        @Body req: LoginRequest
    ): Call<JsonObject>
}