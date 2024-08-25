package com.prodevzla.pokedex

import com.prodevzla.pokedex.domain.GetPokemonTypesUseCase
import com.prodevzla.pokedex.model.domain.PokemonType
import com.prodevzla.pokedex.model.domain.Result
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test


class GetPokemonTypesUseCaseTest {

    private val useCase = GetPokemonTypesUseCase(repository)

    @Test
    fun `verify first item is all types`() = runBlocking {
        val generations: Result<List<PokemonType>> = useCase.invoke().toList().first()
        assertTrue(generations is Result.Success)

        generations as Result.Success

        assertEquals(generations.data.first().id, 0)

        assertEquals(generations.data.first().name, "all types")
    }

    @Test
    fun `verify number of types`() = runBlocking {
        val generations: Result<List<PokemonType>> = useCase.invoke().toList().first()
        assertTrue(generations is Result.Success)

        generations as Result.Success

        assertEquals(generations.data.size, 22)

    }

}