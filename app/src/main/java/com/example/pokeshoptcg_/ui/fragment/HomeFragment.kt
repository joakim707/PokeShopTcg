package com.example.pokeshoptcg_.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.widget.doOnTextChanged
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
    private lateinit var searchBar: EditText
    private lateinit var typeSpinner: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        searchBar = view.findViewById(R.id.searchBar)
        typeSpinner = view.findViewById(R.id.typeSpinner)

        adapter = ProductAdapter(mutableListOf()) { card -> viewModel.toggleFavorite(card) }
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        // Observe filtered cards 
        viewModel.filteredCards.observe(viewLifecycleOwner) { adapter.updateProducts(it) }

        // ProgressBar
        viewModel.isLoading.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        // SearchBar text change
        searchBar.doOnTextChanged { text, _, _, _ ->
            viewModel.setSearchQuery(text.toString())
        }

        // Spinner setup
        val types = listOf("All", "Fire", "Water", "Grass", "Lightning", "Psychic", "Fighting", "Metal", "Fairy", "Dragon", "Dark", "Colorless")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = spinnerAdapter
        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selected = types[position]
                viewModel.setSelectedType(selected)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        return view
    }
}
