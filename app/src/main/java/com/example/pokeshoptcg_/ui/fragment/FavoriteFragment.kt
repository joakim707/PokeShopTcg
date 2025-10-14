package com.example.pokeshoptcg_.ui.fragment

import android.os.Bundle
import android.view.*
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.favorite_fragment, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewFavorites)
        adapter = ProductAdapter(mutableListOf()) { card -> viewModel.toggleFavorite(card) }

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        viewModel.favorites.observe(viewLifecycleOwner) { adapter.updateProducts(it) }

        return view
    }
}
