package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

inline fun <T> List<T>.filterIf(condition: Boolean, predicate: (T) -> Boolean): List<T> {
    return if (condition) {
        this.filter(predicate)
    } else {
        this
    }
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> { Result.Success(it) }
        .flowOn(Dispatchers.IO)
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(it)) }
}
