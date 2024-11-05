package com.prodevzla.pokedex.presentation.util

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.FilterType
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonAbility
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.model.PokemonSpec

object PreviewData {
    val pokemon1 = Pokemon(
        id = 4,
        name = "Charmander",
        types = listOf(
            PokemonType(
                id = 10,
                name = UiText.DynamicString("Fire")
            )
        ),
        generation = 1,
        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png",
        isSaved = false,
        abilities = listOf(1, 2)
        //gameVersions = emptyList()
    )

    val pokemon2 = Pokemon(
        id = 5,
        name = "Charizard",
        types = listOf(
            PokemonType(
                id = 10,
                name = UiText.DynamicString("Fire")
            )
        ),
        generation = 1,
        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png",
        isSaved = true,
        abilities = listOf(1, 2)
        //gameVersions = emptyList()
    )

    val pokemonList = listOf(pokemon1, pokemon2)


    val filter1 = Filter(
        dialogTitle = UiText.DynamicString("Select generation"),
        weight = 1.0f,
        selection = 1,
        values = listOf(
            PokemonGeneration(
                id = 1,
                name = UiText.DynamicString("Gen I")
            )
        ),
        type = FilterType.GENERATION,
    )

    val filter2 = Filter(
        dialogTitle = UiText.DynamicString("Select type"),
        weight = 1.0f,
        selection = 10,
        values = listOf(
            PokemonType(
                id = 10,
                name = UiText.DynamicString("Fire")
            )
        ),
        type = FilterType.TYPE,
    )

    val filterList = listOf(filter1, filter2)

    val pokemonSpec = PokemonSpec(
        height = UiText.DynamicString("120 cm"),
        weight = UiText.DynamicString("30 Kg"),
        //genderRate = 8498,
        flavorText = LoremIpsum(words = 25).values.toList().first().toString(),
        cry = "https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/1.ogg",
        statePlayVoiceover = AudioPlaybackState.IDLE,
        statePlayCry = AudioPlaybackState.IDLE
    )

    private val pokemonAbility1 = PokemonAbility(
        id = 1,
        name = "Run away",
        description = "Enables a sure getaway from wild Pokemon",
        isHidden = false
    )

    private val pokemonAbility2 = PokemonAbility(
        id = 2,
        name = "Hustle",
        description = "Boosts the attack stat, but lowers accuracy",
        isHidden = true
    )

    val pokemonAbilityList = listOf(pokemonAbility1, pokemonAbility2)

    val ability1 = Ability(
        id = 6260,
        name = "Overgrow",
        flavorText = "Overgrow",
        shortEffect = "Powers up Grass-type moves when the Pokemon's HP is low",
        longEffect = LoremIpsum(words = 25).values.toList().first().toString()
    )

    val ability2 = Ability(
        id = 6261,
        name = "Intimidate",
        flavorText = "Intimidate",
        shortEffect = "Lowers opponents' Attack one stage upon entering battle",
        longEffect = LoremIpsum(words = 25).values.toList().first().toString()
    )

    val abilities = listOf(ability1, ability2)

}