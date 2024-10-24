package com.prodevzla.pokedex.data.mapper

import com.prodevzla.pokedex.data.GetAbilitiesQuery
import com.prodevzla.pokedex.domain.model.Ability


class AbilitiesMapper : Mapper<GetAbilitiesQuery.Pokemon_v2_ability, Ability, Any> {

    override fun mapToDomain(api: List<GetAbilitiesQuery.Pokemon_v2_ability>): List<Ability> {
        return api.map { mapToDomain(it) }
    }

    override fun mapToDomain(api: GetAbilitiesQuery.Pokemon_v2_ability): Ability {
        return Ability(
            id = api.id,
            name = api.pokemon_v2_abilitynames.first().name,
            flavorText = null,
            shortEffect = api.pokemon_v2_abilityeffecttexts.firstOrNull()?.short_effect?.replace(
                oldValue = "\n",
                newValue = ""
            ) ?: "",
            longEffect = null
        )
    }

    override fun mapToEntity(domain: Ability) = Any()


}
