package com.prodevzla.pokedex

import com.prodevzla.pokedex.domain.GetFiltersUseCase
import com.prodevzla.pokedex.domain.GetPokemonGenerationsUseCase
import com.prodevzla.pokedex.domain.GetPokemonTypesUseCase
import com.prodevzla.pokedex.domain.model.Filter
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test


class GetFiltersUseCaseTest {

    private val useCase = GetFiltersUseCase(
        getPokemonGenerationsUseCase = GetPokemonGenerationsUseCase(repository),
        getPokemonTypesUseCase = GetPokemonTypesUseCase(repository),
    )

    @Test
    fun `verify number of filters`() = runBlocking {
        val filters: List<Filter>? = useCase.invoke(
            generationFilter = MutableStateFlow(0),
            typeFilter = MutableStateFlow(0),
            onClickGeneration = {},
            onClickType = {}

        ).first()

        assertTrue(filters != null)

        assertEquals(filters?.size, 2)

    }


}