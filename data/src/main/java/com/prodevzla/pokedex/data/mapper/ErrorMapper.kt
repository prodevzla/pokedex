package com.prodevzla.pokedex.data.mapper

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Operation
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.prodevzla.pokedex.domain.model.DataError

//internal inline fun <T, R> executeNetworkCall(
//    networkCall: () -> Response<T>,
//    processResponse: (T?) -> R
//): Result<R> {
//    return try {
//        val response = networkCall()
//        if (response.isSuccessful) {
//            Result.Success(processResponse(response.body()))
//        } else {
//            val error = when (response.code()) {
//                408 -> DataError.Network.REQUEST_TIMEOUT
//                429 -> DataError.Network.TOO_MANY_REQUESTS
//                413 -> DataError.Network.PAYLOAD_TOO_LARGE
//                500, 502, 503, 504 -> DataError.Network.SERVER_ERROR
//                else -> DataError.Network.UNKNOWN
//            }
//            Result.Error(error)
//        }
//    } catch (e: Exception) {
//        val error = when (e) {
//            is java.net.UnknownHostException -> DataError.Network.NO_INTERNET
//            is java.net.SocketTimeoutException -> DataError.Network.REQUEST_TIMEOUT
//            is java.io.IOException -> DataError.Network.SERVER_ERROR
//            is kotlinx.serialization.SerializationException -> DataError.Network.SERIALIZATION
//            else -> DataError.Network.UNKNOWN
//        }
//        e.printStackTrace()
//        Result.Error(error)
//    }
//}

internal suspend inline fun <T : Operation.Data, R> executeApolloCall(
    query: () -> ApolloCall<T>,
    processResponse: (T?) -> R
): R {
    return try {
        val response = query().execute()
        processResponse(response.data)
    } catch (e: Exception) {
        Firebase.crashlytics.recordException(e)
        val error = when (e) {
            is java.net.UnknownHostException -> DataError.NoInternet
            is java.net.SocketTimeoutException -> DataError.RequestTimeOut
            is java.io.IOException -> DataError.ServerError
            else -> DataError.Unknown
        }
        e.printStackTrace()
        throw error
    }
}
