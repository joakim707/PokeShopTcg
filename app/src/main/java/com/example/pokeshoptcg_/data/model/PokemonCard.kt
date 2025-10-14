package com.example.pokeshoptcg_.data.model

data class PokemonResponse(
    val data: List<PokemonCard>
)

data class PokemonCard(
    val id: String,
    val name: String,
    val types: List<String>?,
    val rarity: String?,
    val cardmarket: CardMarket?,
    val images: PokemonImages
)

data class PokemonImages(
    val small: String,
    val large: String
)

data class CardMarket(
    val prices: CardPrices?
)

data class CardPrices(
    val averageSellPrice: Double?
)
