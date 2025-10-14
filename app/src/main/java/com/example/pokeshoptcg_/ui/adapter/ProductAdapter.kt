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
import com.example.pokeshoptcg_.data.model.PokemonCard

class ProductAdapter(
    private val products: MutableList<PokemonCard>
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

        // safe image load (coil) — use large if present otherwise small
        val imageUrl = card.images.large.ifEmpty { card.images.small }
        holder.imageProduct.load(imageUrl) {
            placeholder(R.drawable.ic_launcher_foreground)
            crossfade(true)
        }

        holder.textName.text = card.name ?: "Unknown"
        holder.textType.text = "Type : ${card.types?.joinToString(", ") ?: "Inconnu"}"
        holder.textRarity.text = "Rareté : ${card.rarity ?: "Non spécifiée"}"

        val avgPrice = card.cardmarket?.prices?.averageSellPrice
        holder.textPrice.text = if (avgPrice != null) "Prix moyen : €${String.format("%.2f", avgPrice)}" else "Prix moyen : N/A"

        // Favori : on peut stocker l'état via tag (simple) ou gérer un champ isFavorite si tu veux persister
        val isFav = holder.buttonFavorite.tag as? Boolean ?: false
        holder.buttonFavorite.setImageResource(if (isFav) R.drawable.ic_favorite else R.drawable.ic_favorite_border)

        holder.buttonFavorite.setOnClickListener {
            val newFav = !(holder.buttonFavorite.tag as? Boolean ?: false)
            holder.buttonFavorite.tag = newFav
            holder.buttonFavorite.setImageResource(if (newFav) R.drawable.ic_favorite else R.drawable.ic_favorite_border)

            // Optionnel : ajouter animation "pop"
            holder.buttonFavorite.animate().scaleX(1.2f).scaleY(1.2f).setDuration(120)
                .withEndAction {
                    holder.buttonFavorite.animate().scaleX(1f).scaleY(1f).start()
                }.start()
        }

        // Optionnel : clique sur la carte pour ouvrir détails (non implémenté ici)
    }

    override fun getItemCount(): Int = products.size

    // Méthode utile pour mettre à jour les données depuis le fragment
    fun updateProducts(newProducts: List<PokemonCard>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }
}
