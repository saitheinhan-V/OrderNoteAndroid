package com.note.order.repository

import android.app.Application
import com.note.order.APICall.NetworkCall
import com.note.order.APICall.RequestBuilder
import com.note.order.APICall.Service
import com.note.order.GlobalConstant
import com.note.order.entity.BrandItem

class APIDataRepository(application: Application) {

    private val apiAuth = RequestBuilder.createService(Service::class.java, GlobalConstant().BASE_URL, "")

    fun getAllBrand() = NetworkCall<MutableList<BrandItem>>().makeCall(apiAuth.getAllBrand())
}