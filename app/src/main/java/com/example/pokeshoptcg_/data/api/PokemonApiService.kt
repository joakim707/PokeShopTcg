package com.example.pokeshoptcg_.data.api

import com.example.pokeshoptcg_.data.model.PokemonCard
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PokemonApiService {

    @Headers("X-Api-Key: d44c2a62-ed76-43a3-863d-906bce21505c")
    @GET("cards")
    suspend fun getPokemonCards(
        @Query("pageSize") pageSize: Int = 10
    ): Response<PokemonResponse>
}

data class PokemonResponse(
    val data: List<PokemonCard>
)
