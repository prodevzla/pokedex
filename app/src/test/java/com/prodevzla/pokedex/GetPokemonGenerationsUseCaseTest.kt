package com.prodevzla.pokedex

import com.prodevzla.pokedex.data.repository.PokemonRepositoryImpl
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.usecase.GetPokemonGenerationsUseCase
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.model.UiText
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


class GetPokemonGenerationsUseCaseTest {

    private val repository: PokemonRepositoryImpl = mock(PokemonRepositoryImpl::class.java)

    private val useCase = GetPokemonGenerationsUseCase(repository)

    @Before
    fun setup() {
        val mockResult =
            (1..9).map { id ->
                PokemonGeneration(
                    id = id,
                    name = UiText.DynamicString(value = "generation-$id")
                )
            }

        `when`(repository.getPokemonGenerations()).thenReturn(
            flowOf(mockResult)
        )
    }

    @Test
    fun `verify first item is all gens`() = runBlocking {
        val generations: Result<List<Filterable>> = useCase.invoke().first { it is Result.Success }
        assertTrue(generations is Result.Success)

        generations as Result.Success

        assertEquals(0, generations.data.first().id)

        assertEquals(
            com.prodevzla.pokedex.domain.R.string.all_gens,
            (generations.data.first().name as UiText.StringResource).resId
        )
    }

    @Test
    fun `verify number of generations`() = runBlocking {
        val generations: Result<List<Filterable>> = useCase.invoke().toList().first { it is Result.Success }
        assertTrue(generations is Result.Success)

        generations as Result.Success

        assertEquals(10, generations.data.size)

    }

    @Test
    fun `verify generations label`() = runBlocking {
        val generations: Result<List<Filterable>> = useCase.invoke().toList().first { it is Result.Success }
        assertTrue(generations is Result.Success)

        generations as Result.Success

        generations.data.drop(1).forEach {
            assertFalse((it.name as UiText.DynamicString).value.contains("generation"))
        }

    }
}