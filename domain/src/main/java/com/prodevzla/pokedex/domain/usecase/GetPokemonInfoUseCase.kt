package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class GetPokemonInfoUseCase(
    private val repository: PokemonRepository
) {

    //shall this be in a separate use case?
    private fun convertHeightToCm(height: Int) = height * 10
    //shall this be in a separate use case?
    private fun convertWeightToKg(weight: Int) = weight / 10

    operator fun invoke(id: Int): Flow<Result<PokemonInfoUI>> =
        repository.getPokemonInfo(id)
            .map<PokemonInfo, Result<PokemonInfoUI>> { result ->
                Result.Success(
                    result.toUI(
                        height = "${convertHeightToCm(result.height)} cm",
                        weight = "${convertWeightToKg(result.weight)} Kg",
                    )
                )
            }
            .onStart { emit(Result.Loading) }
            .catch { e ->
                println(e)
                emit(Result.Error(e))
            }.flowOn(Dispatchers.IO)

}


data class PokemonInfoUI(
    val height: String,
    val weight: String,
    val genderRate: Int,
    val flavorText: String,
    val cry: String,
)

fun PokemonInfo.toUI(height: String, weight: String): PokemonInfoUI {
    return PokemonInfoUI(
        height = height,
        weight = weight,
        genderRate = this.genderRate,
        flavorText = this.flavorText.replace("\n", " "),
        cry = this.cry
    )
}

