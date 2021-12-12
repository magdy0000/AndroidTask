package com.example.androidtask.data.network

import com.bumptech.glide.load.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkState constructor(val status: Status, val msg: Any? = null, val data: Any? = null) {

    enum class Status {
        RUNNING, FAILED, SUCCESS
    }

    companion object {
        fun getLoaded(dataSuccess: Any?): NetworkState {
            return NetworkState(Status.SUCCESS, data = dataSuccess)
        }

        var LOADING: NetworkState = NetworkState(Status.RUNNING)

        fun getErrorMessage(throwable: Throwable): NetworkState {
            return when (throwable) {
                is IOException -> {
                    NetworkState(Status.FAILED, "No Connection ")
                }
                is SocketTimeoutException -> {
                    NetworkState(Status.FAILED, "Bad Connection")
                }
                is HttpException -> {
                    val code = throwable.statusCode
                    NetworkState(Status.FAILED, "HttpException  code $code")
                }
                else -> {
                    NetworkState(Status.FAILED, "Error")
                }
            }
        }

        fun getErrorMessage(massage: String): NetworkState {
            return NetworkState(Status.FAILED, massage)

        }

    }
} // class of NetworkState