package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.viewcase

import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.model.PokemonInfoUiState
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.model.PokemonSpec

class TransformPokemonInfoIntoModelViewCase(
    private val getHeightLabelViewCase: GetHeightLabelViewCase,
    private val getWeightLabelViewCase: GetWeightLabelViewCase,
) {

    operator fun invoke(
        pokemonInfo: PokemonInfo,
        statePlayVoiceover: AudioPlaybackState,
        statePlayCry: AudioPlaybackState,
        pokemon: Pokemon,
    ): PokemonInfoUiState.Content {
        val spec = PokemonSpec(
            height = getHeightLabelViewCase(pokemonInfo.height),
            weight = getWeightLabelViewCase(pokemonInfo.weight),
//            genderRate = pokemonInfo.genderRate,
            flavorText = pokemonInfo.flavorText,
            cry = pokemonInfo.cry,
            statePlayVoiceover = statePlayVoiceover,
            statePlayCry = statePlayCry
        )

        return PokemonInfoUiState.Content(
            spec = spec,
            abilities = pokemonInfo.abilities,
            pokemonType = pokemon.types.first()
        )
    }

}
