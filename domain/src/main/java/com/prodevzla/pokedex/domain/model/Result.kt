package com.prodevzla.pokedex.domain.model

sealed interface Result<out D> {
    data class Success<out D>(val data: D):
        Result<D>
    data class Error(val error: Throwable):
        Result<Nothing>
    data object Loading : Result<Nothing>
}

sealed class DataError: Throwable() {
    data object RequestTimeOut: DataError()
    data object TooManyRequests: DataError()
    data object NoInternet: DataError()
    data object PayloadTooLarge: DataError()
    data object ServerError: DataError()
    data object Unknown: DataError()
}
