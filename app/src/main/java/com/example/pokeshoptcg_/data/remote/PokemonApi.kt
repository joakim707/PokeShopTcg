package com.example.pokeshoptcg_.data.remote

import com.example.pokeshoptcg_.data.model.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {

    // Obtenir les cartes Pokémon (pageSize=20 par défaut)
    @GET("cards")
    fun getCards(
        @Query("pageSize") pageSize: Int = 20
    ): Call<PokemonResponse>
}
