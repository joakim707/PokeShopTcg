package com.example.pokeshoptcg_.data.model

data class PokemonCard(
    val id: String,
    val name: String?,
    val types: List<String>?,
    val rarity: String?,
    val images: Images,
    val cardmarket: CardMarket?
)

data class Images(
    val small: String,
    val large: String
)

data class CardMarket(
    val prices: Prices?
)

data class Prices(
    val averageSellPrice: Double?
)
