package com.prodevzla.pokedex.data.mapper

import com.prodevzla.pokedex.data.GetAbilityQuery
import com.prodevzla.pokedex.domain.model.Ability

fun GetAbilityQuery.Pokemon_v2_ability.toDomain(): Ability {
    return Ability(
        id = this.id,
        name = this.pokemon_v2_abilitynames.first().name,
        flavorText = this.pokemon_v2_abilityflavortexts.first().flavor_text,
        shortEffect = this.pokemon_v2_abilityeffecttexts.first().short_effect,
        longEffect = this.pokemon_v2_abilityeffecttexts.first().effect,
    )
}

fun List<GetAbilityQuery.Pokemon_v2_ability>.toDomain(): Ability {
    return this.map { it.toDomain() }.first()
}