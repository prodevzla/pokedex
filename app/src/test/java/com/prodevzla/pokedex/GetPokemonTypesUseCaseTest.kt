package com.prodevzla.pokedex

import com.prodevzla.pokedex.data.repository.PokemonRepositoryImpl
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.domain.usecase.GetPokemonTypesUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


class GetPokemonTypesUseCaseTest {

    private val repository: PokemonRepositoryImpl = mock(PokemonRepositoryImpl::class.java)

    private val useCase = GetPokemonTypesUseCase(repository)

    @Before
    fun setup() {
        val mockResult = Result.Success(
            (1..22).map { id ->
                PokemonType(
                    id = id,
                    name = UiText.DynamicString(value = "type-$id")
                )
            }
        )

        `when`(repository.getPokemonTypes()).thenReturn(
            flowOf(mockResult)
        )
    }

    @Test
    fun `verify first item is all types`() = runBlocking {
        val generations: Result<List<Filterable>> = useCase.invoke().toList().first()
        assertTrue(generations is Result.Success)

        generations as Result.Success

        assertEquals(0, generations.data.first().id)

        assertEquals(
            com.prodevzla.pokedex.domain.R.string.all_types,
            (generations.data.first().name as UiText.StringResource).resId,
        )
    }

    @Test
    fun `verify number of types`() = runBlocking {
        val generations: Result<List<Filterable>> = useCase.invoke().toList().first()
        assertTrue(generations is Result.Success)

        generations as Result.Success

        assertEquals(23, generations.data.size)

    }

}