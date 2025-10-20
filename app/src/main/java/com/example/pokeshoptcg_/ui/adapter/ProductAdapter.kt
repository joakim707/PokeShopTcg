package com.example.pokeshoptcg_.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokeshoptcg_.R
import com.example.pokeshoptcg_.data.db.PokemonCardEntity

class ProductAdapter(
    private val products: MutableList<PokemonCardEntity>,
    private val onFavoriteClick: (PokemonCardEntity) -> Unit,
    private val onItemClick: (PokemonCardEntity) -> Unit            // <— ajouté
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
        val textName: TextView = itemView.findViewById(R.id.textProductName)
        val textType: TextView = itemView.findViewById(R.id.textProductType)
        val textRarity: TextView = itemView.findViewById(R.id.textProductRarity)
        val textPrice: TextView = itemView.findViewById(R.id.textProductPrice)
        val buttonFavorite: ImageButton = itemView.findViewById(R.id.buttonFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val card = products[position]

        holder.imageProduct.load(card.imageUrl)
        holder.textName.text = card.name
        holder.textType.text = "Type : ${card.type ?: "Inconnu"}"
        holder.textRarity.text = "Rareté : ${card.rarity ?: "N/A"}"
        holder.textPrice.text = "Prix : ${card.price ?: 0.0}€"

        holder.buttonFavorite.setImageResource(
            if (card.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        )
        holder.buttonFavorite.setOnClickListener { onFavoriteClick(card) }

        // ↓↓↓ clic sur toute la carte => ouvre le détail
        holder.itemView.setOnClickListener { onItemClick(card) }
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(newProducts: List<PokemonCardEntity>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }
}
