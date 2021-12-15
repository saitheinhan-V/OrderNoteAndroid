package com.note.order.APICall

class Resource<T> private constructor(val status: Status, val data: T?, val errorMsg: String?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T> error(errorMsg: String?): Resource<T> {
            return Resource(Status.ERROR, null, errorMsg)
        }
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}