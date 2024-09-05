package com.prodevzla.pokedex

import com.prodevzla.pokedex.data.repository.PokemonRepositoryImpl
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.domain.usecase.GetFiltersUseCase
import com.prodevzla.pokedex.domain.usecase.GetPokemonGenerationsUseCase
import com.prodevzla.pokedex.domain.usecase.GetPokemonTypesUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


class GetFiltersUseCaseTest {

    private val repository: PokemonRepositoryImpl = mock(PokemonRepositoryImpl::class.java)

    private val useCase = GetFiltersUseCase(
        getPokemonGenerationsUseCase = GetPokemonGenerationsUseCase(repository),
        getPokemonTypesUseCase = GetPokemonTypesUseCase(repository),
    )

    @Before
    fun setup() {
        val mockTypes = Result.Success(
            (1..22).map { id ->
                PokemonType(
                    id = id,
                    name = UiText.DynamicString(value = "type-$id")
                )
            }
        )

        `when`(repository.getPokemonTypes()).thenReturn(
            flowOf(mockTypes)
        )

        val mockGenerations = Result.Success(
            (1..9).map { id ->
                PokemonGeneration(
                    id = id,
                    name = UiText.DynamicString(value = "generation-$id")
                )
            }
        )

        `when`(repository.getPokemonGenerations()).thenReturn(
            flowOf(mockGenerations)
        )
    }

    @Test
    fun `verify number of filters`() = runBlocking {

        //val context = ApplicationProvider.getApplicationContext<Context>()

        val filters: List<Filter>? = useCase.invoke(
            generationFilter = MutableStateFlow(0),
            typeFilter = MutableStateFlow(0),

        ).first()

        assertTrue(filters != null)

        assertEquals(filters?.size, 2)

    }


}