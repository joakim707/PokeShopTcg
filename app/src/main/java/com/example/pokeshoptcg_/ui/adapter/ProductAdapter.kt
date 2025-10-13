package com.example.pokeshoptcg_.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeshoptcg_.R
import com.example.pokeshoptcg_.data.model.Product

class ProductAdapter(private val products: MutableList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
        val textName: TextView = itemView.findViewById(R.id.textProductName)
        val textPrice: TextView = itemView.findViewById(R.id.textProductPrice)
        val buttonFavorite: ImageButton = itemView.findViewById(R.id.buttonFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.imageProduct.setImageResource(product.imageResId)
        holder.textName.text = product.name
        holder.textPrice.text = product.price

        holder.buttonFavorite.setImageResource(
            if (product.isFavorite)
                android.R.drawable.btn_star_big_on
            else
                android.R.drawable.btn_star_big_off
        )

        holder.buttonFavorite.setOnClickListener {
            product.isFavorite = !product.isFavorite
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = products.size
}