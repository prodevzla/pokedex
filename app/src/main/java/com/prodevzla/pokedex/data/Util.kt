package com.prodevzla.pokedex.data

import com.prodevzla.pokedex.model.DataError
import com.prodevzla.pokedex.model.Result
import retrofit2.Response

internal inline fun <T, R> executeNetworkCall(
    networkCall: () -> Response<T>,
    processResponse: (T?) -> R
): Result<R, DataError.Network> {
    return try {
        val response = networkCall()
        if (response.isSuccessful) {
            Result.Success(processResponse(response.body()))
        } else {
            val error = when (response.code()) {
                408 -> DataError.Network.REQUEST_TIMEOUT
                429 -> DataError.Network.TOO_MANY_REQUESTS
                413 -> DataError.Network.PAYLOAD_TOO_LARGE
                500, 502, 503, 504 -> DataError.Network.SERVER_ERROR
                else -> DataError.Network.UNKNOWN
            }
            Result.Error(error)
        }
    } catch (e: Exception) {
        val error = when (e) {
            is java.net.UnknownHostException -> DataError.Network.NO_INTERNET
            is java.net.SocketTimeoutException -> DataError.Network.REQUEST_TIMEOUT
            is java.io.IOException -> DataError.Network.SERVER_ERROR
            is kotlinx.serialization.SerializationException -> DataError.Network.SERIALIZATION
            else -> DataError.Network.UNKNOWN
        }
        e.printStackTrace()
        Result.Error(error)
    }
}