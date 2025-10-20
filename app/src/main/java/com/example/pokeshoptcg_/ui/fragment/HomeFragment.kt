package com.example.pokeshoptcg_.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeshoptcg_.R
import com.example.pokeshoptcg_.ui.adapter.ProductAdapter
import com.example.pokeshoptcg_.ui.viewmodel.PokemonViewModel

class HomeFragment : Fragment() {

    private val viewModel: PokemonViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)

        adapter = ProductAdapter(
            products = mutableListOf(),
            onFavoriteClick = { card -> viewModel.toggleFavorite(card) },
            onItemClick = { card ->
                // Map PokemonCardEntity -> ProductUi
                val ui = ProductUi(
                    id = card.id,
                    name = card.name ?: "Sans nom",   // <- évite l’erreur String? -> String
                    imageUrl = card.imageUrl,
                    setName = null,                   // <- pas dans l’entity
                    rarity = card.rarity,
                    number = null,                    // <- pas dans l’entity
                    types = card.type?.let { listOf(it) } ?: emptyList(),
                    marketPrice = card.price          // <- déjà Double?
                )
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, ProductFragment.newInstance(ui)) // <- bon ID
                    .addToBackStack(null)
                    .commit()
            }
        )

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        // Observe filtered cards 
        viewModel.filteredCards.observe(viewLifecycleOwner) { adapter.updateProducts(it) }

        // ProgressBar
        viewModel.isLoading.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        return view
    }
}
