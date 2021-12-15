package com.note.order.APICall

import org.json.JSONObject
import retrofit2.Response

object ErrorHandler {

    fun apiError(response: Response<*>): String? {
        val jsonObject: JSONObject
        var message = ""
        try {
            val errorBody = response.errorBody()?.string()
            jsonObject = JSONObject(errorBody)
            message = jsonObject.getString("message")
        } catch (e: Exception) {
            e.printStackTrace()
            message = "Server Response Error"
        }
        return message
    }

    fun networkError(message: String?): String? {
        return message
    }

}