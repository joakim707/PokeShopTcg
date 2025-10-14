package com.example.pokeshoptcg_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeshoptcg_.data.model.PokemonCard
import com.example.pokeshoptcg_.data.model.PokemonResponse
import com.example.pokeshoptcg_.data.remote.RetrofitInstance
import com.example.pokeshoptcg_.ui.adapter.ProductAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Assure-toi que le nom du layout correspond (home_fragment.xml)
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewProducts)
        progressBar = view.findViewById(R.id.progressBar)

        // setup RecyclerView
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
        productAdapter = ProductAdapter(mutableListOf())
        recyclerView?.adapter = productAdapter

        // Lancer le chargement
        fetchPokemonCards()
    }

    override fun onDestroyView() {
        // éviter fuite de view references
        recyclerView = null
        progressBar = null
        super.onDestroyView()
    }

    private fun showLoading(show: Boolean) {
        // safe access : use view lifecycle references
        progressBar?.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun fetchPokemonCards() {
        // Afficher loader
        showLoading(true)

        // Appel Retrofit (asynchrone)
        RetrofitInstance.api.getCards().enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                // Le fragment peut avoir été détaché — on vérifie
                if (!isAdded) {
                    showLoading(false)
                    return
                }

                showLoading(false)

                if (response.isSuccessful) {
                    val body = response.body()
                    val cards: List<PokemonCard> = body?.data ?: emptyList()

                    // Mettre à jour l'adapter en toute sécurité
                    productAdapter.updateProducts(cards)
                } else {
                    // Message d'erreur convivial
                    context?.let {
                        Toast.makeText(it, "Erreur API: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                // Le fragment peut avoir été détaché — on vérifie
                if (!isAdded) return

                showLoading(false)
                context?.let {
                    Toast.makeText(it, "Erreur réseau : ${t.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
