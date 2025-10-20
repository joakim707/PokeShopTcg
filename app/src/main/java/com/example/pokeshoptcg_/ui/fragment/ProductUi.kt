package com.example.pokeshoptcg_.ui.fragment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUi(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val setName: String?,
    val rarity: String?,
    val number: String?,
    val types: List<String> = emptyList(),
    val marketPrice: Double?
) : Parcelable
