package com.prodevzla.pokedex.data.mapper

import com.prodevzla.pokedex.data.GetGameVersionGroupsQuery
import com.prodevzla.pokedex.domain.model.GameVersion
import com.prodevzla.pokedex.domain.model.GameVersionGroup
import com.prodevzla.pokedex.domain.model.UiText

fun List<GetGameVersionGroupsQuery.Pokemon_v2_versiongroup>.toDomain(): List<GameVersionGroup> {
    return this.map { it.toDomain() }
}

fun GetGameVersionGroupsQuery.Pokemon_v2_versiongroup.toDomain(): GameVersionGroup {
    return GameVersionGroup(
        id = this.id,
        name = UiText.DynamicString(this.name),
        generation = this.generation_id!!,
        versions = this.pokemon_v2_versions.map { it.toDomain() }
    )
}

fun GetGameVersionGroupsQuery.Pokemon_v2_version.toDomain(): GameVersion {
    return GameVersion(
        id = this.id,
        name = this.pokemon_v2_versionnames.first().name
    )
}
