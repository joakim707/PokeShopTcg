package com.example.pokeshoptcg_.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_cards")
data class PokemonCardEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val imageUrl: String?,
    val rarity: String?,
    val type: String?,
    val price: Double?,
    val isFavorite: Boolean = false
)
