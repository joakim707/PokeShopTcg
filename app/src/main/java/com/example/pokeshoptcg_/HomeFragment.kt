package com.example.pokeshoptcg_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeshoptcg_.R
import com.example.pokeshoptcg_.ui.adapter.ProductAdapter
import com.example.pokeshoptcg_.viewmodel.HomeViewModel
import com.example.pokeshoptcg_.util.Result

class HomeFragment : Fragment() {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var viewModel: HomeViewModel
    private var progressBar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recyclerViewProducts)

        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
        productAdapter = ProductAdapter(mutableListOf())
        recyclerView?.adapter = productAdapter

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Observer LiveData
        viewModel.cards.observe(viewLifecycleOwner) { result ->
            progressBar?.visibility = View.GONE
            when (result) {
                is Result.Success -> productAdapter.updateProducts(result.data)
                is Result.Error -> Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
            }
        }

        // Lancer fetch
        progressBar?.visibility = View.VISIBLE
        viewModel.fetchCardsWithRetry()
    }

    override fun onDestroyView() {
        recyclerView = null
        progressBar = null
        super.onDestroyView()
    }
}
