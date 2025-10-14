package com.example.pokeshoptcg_.data.model

data class Product(
    val name: String,
    val price: String,
    val imageResId: Int,
    var isFavorite: Boolean = false,
    )
