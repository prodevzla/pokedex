package com.prodevzla.pokedex.domain.model

data class GameVersion(
    val id: Int,
    val name: String, //for example: Red or Blue or Yellow or Silver
)

data class GameVersionGroup(
    override val id: Int,
    override val name: UiText, //for example: Red-Blue or Yellow
    val generation: Int,
    val versions: List<GameVersion>
): Filterable
