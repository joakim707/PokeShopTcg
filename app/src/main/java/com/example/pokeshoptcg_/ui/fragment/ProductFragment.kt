package com.example.pokeshoptcg_.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import com.example.pokeshoptcg_.R
import com.example.pokeshoptcg_.databinding.ProductFragmentBinding
import com.google.android.material.chip.Chip
import java.util.Locale

class ProductFragment : Fragment() {

    private var _binding: ProductFragmentBinding? = null
    private val binding get() = _binding!!

    private val product: ProductUi by lazy {
        requireArguments().getParcelable<ProductUi>(ARG_PRODUCT)
            ?: error("ProductFragment: argument ProductUi manquant")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
        bindProduct()
    }

    private fun bindProduct() {
        // Image
        binding.ivImage.contentDescription = product.name
        binding.ivImage.load(product.imageUrl)

        // Nom
        binding.tvName.text = product.name

        // Set + rareté
        val setRarity = listOfNotNull(product.setName, product.rarity).joinToString(" • ")
        binding.tvSetRarity.text = if (setRarity.isBlank()) getString(R.string.unknown) else setRarity

        // Types
        binding.chipsTypes.removeAllViews()
        if (product.types.isEmpty()) {
            binding.chipsTypes.visibility = View.GONE
        } else {
            binding.chipsTypes.visibility = View.VISIBLE
            product.types.forEach { t ->
                val chip = Chip(requireContext()).apply {
                    text = t
                    isCheckable = false
                }
                binding.chipsTypes.addView(chip)
            }
        }

        // Prix
        binding.tvPrice.text = product.marketPrice?.let {
            String.format(Locale.US, "~ %.2f $", it)
        } ?: getString(R.string.price_na)

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val ARG_PRODUCT = "arg_product"
        fun newInstance(p: ProductUi) = ProductFragment().apply {
            arguments = bundleOf(ARG_PRODUCT to p)
        }
    }
}
