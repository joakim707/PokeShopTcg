package com.example.pokeshoptcg_.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    // ✅ Créer un client OkHttp avec l'intercepteur pour la clé API
    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-Api-Key", "d44c2a62-ed76-43a3-863d-906bce21505c") // <- Remplace par ta clé réelle
                .build()
            chain.proceed(request)
        }
        .connectTimeout(30, TimeUnit.SECONDS)  // Timeout connexion
        .readTimeout(30, TimeUnit.SECONDS)     // Timeout lecture
        .writeTimeout(30, TimeUnit.SECONDS)    // Timeout écriture
        .build()

    // Retrofit avec ce client
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.pokemontcg.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: PokemonApi by lazy {
        retrofit.create(PokemonApi::class.java)
    }
}
