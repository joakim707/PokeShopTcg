package com.example.pokeshoptcg_.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-Api-Key", "d44c2a62-ed76-43a3-863d-906bce21505c") // <- remplace par ta clé API
                .build()
            chain.proceed(request)
        }
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.pokemontcg.io/v2/") // slash final obligatoire
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: PokemonApi by lazy {
        retrofit.create(PokemonApi::class.java)
    }
}
