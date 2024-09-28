package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPokemonInfoUseCase(
    private val repository: PokemonRepository
) {

    //shall this be in a separate use case?
    private fun convertHeightToCm(height: Int) = height * 10

    //shall this be in a separate use case?
    private fun convertWeightToKg(weight: Int) = weight / 10

    operator fun invoke(id: Int): Flow<Result<PokemonInfoUI>> =
        repository.getPokemonInfo(id)
            .map { result: PokemonInfo ->
                result.toPokemonInfoUI(
                    height = "${convertHeightToCm(result.height)} cm",
                    weight = "${convertWeightToKg(result.weight)} Kg"
                )
            }
            .asResult()

}

data class PokemonInfoUI(
    val height: String,
    val weight: String,
    val genderRate: Int,
    val flavorText: String,
    val cry: String,
    val abilities: List<String>
)

fun PokemonInfo.toPokemonInfoUI(height: String, weight: String): PokemonInfoUI {
    return PokemonInfoUI(
        height = height,
        weight = weight,
        genderRate = this.genderRate,
        flavorText = this.flavorText.replace("\n", " "),
        cry = this.cry,
        abilities  = this.abilities,
    )
}
