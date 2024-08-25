package com.prodevzla.pokedex

import com.apollographql.apollo.ApolloClient
import com.prodevzla.pokedex.data.PokemonRepositoryImpl

private val apolloClient = ApolloClient.Builder()
    .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
    .build()

val repository = PokemonRepositoryImpl(apolloClient)
