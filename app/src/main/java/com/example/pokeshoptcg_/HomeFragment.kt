package com.example.pokeshoptcg_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeshoptcg_.data.model.Product
import com.example.pokeshoptcg_.ui.adapter.ProductAdapter

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewProducts)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 colonnes

        val sampleProducts = mutableListOf(
            Product("Pikachu", "€12.99", R.drawable.ic_launcher_foreground),
            Product("Dracaufeu", "€19.99", R.drawable.ic_launcher_foreground),
            Product("Evoli", "€9.99", R.drawable.ic_launcher_foreground),
            Product("Mewtwo", "€24.99", R.drawable.ic_launcher_foreground),
            Product("Tortank", "€18.49", R.drawable.ic_launcher_foreground),
            Product("Florizarre", "€17.49", R.drawable.ic_launcher_foreground)
        )

        recyclerView.adapter = ProductAdapter(sampleProducts)
    }
}
