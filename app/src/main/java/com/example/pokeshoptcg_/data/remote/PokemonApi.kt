package com.example.pokeshoptcg_.data.remote

import com.example.pokeshoptcg_.data.model.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {
    @GET("cards")
    fun getCards(@Query("pageSize") pageSize: Int = 20): Call<PokemonResponse>
}
