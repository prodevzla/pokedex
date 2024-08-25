package com.prodevzla.pokedex

import com.prodevzla.pokedex.domain.GetPokemonGenerationsUseCase
import com.prodevzla.pokedex.model.domain.PokemonGeneration
import com.prodevzla.pokedex.model.domain.Result
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test


class GetPokemonGenerationsUseCaseTest {

    private val useCase = GetPokemonGenerationsUseCase(repository)

    @Test
    fun `verify first item is all gens`() = runBlocking {
        val generations: Result<List<PokemonGeneration>> = useCase.invoke().toList().first()
        assertTrue(generations is Result.Success)

        generations as Result.Success

        assertEquals(generations.data.first().id, 0)

        assertEquals(generations.data.first().name, "all gens")
    }

    @Test
    fun `verify number of generations`() = runBlocking {
        val generations: Result<List<PokemonGeneration>> = useCase.invoke().toList().first()
        assertTrue(generations is Result.Success)

        generations as Result.Success

        assertEquals(generations.data.size, 10)

    }

    @Test
    fun `verify generations label`() = runBlocking {
        val generations: Result<List<PokemonGeneration>> = useCase.invoke().toList().first()
        assertTrue(generations is Result.Success)

        generations as Result.Success

        generations.data.forEach {
            assertFalse(it.name.contains("generation"))
        }

    }
}