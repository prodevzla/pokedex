package com.prodevzla.pokedex.domain.model

sealed interface Error

sealed interface Result<out D> {
    data class Success<out D>(val data: D):
        Result<D>
    data class Error<out D>(val error: DataError.Network):
        Result<D>
    data object Loading : Result<Nothing>
}

sealed interface DataError: Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }
}
