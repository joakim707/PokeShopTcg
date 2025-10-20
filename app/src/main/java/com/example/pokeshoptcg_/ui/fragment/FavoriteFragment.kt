package com.example.pokeshoptcg_.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeshoptcg_.R
import com.example.pokeshoptcg_.ui.adapter.ProductAdapter
import com.example.pokeshoptcg_.ui.viewmodel.PokemonViewModel

class FavoriteFragment : Fragment() {

    private val viewModel: PokemonViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.favorite_fragment, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewFavorites)

        adapter = ProductAdapter(
            products = mutableListOf(),
            onFavoriteClick = { card -> viewModel.toggleFavorite(card) },
            onItemClick = { card ->
                val ui = ProductUi(
                    id = card.id,
                    name = card.name ?: "Sans nom",
                    imageUrl = card.imageUrl,
                    setName = null,
                    rarity = card.rarity,
                    number = null,
                    types = card.type?.let { listOf(it) } ?: emptyList(),
                    marketPrice = card.price
                )
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, ProductFragment.newInstance(ui))
                    .addToBackStack(null)
                    .commit()
            }
        )

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        viewModel.favorites.observe(viewLifecycleOwner) { adapter.updateProducts(it) }

        return view
    }
}
