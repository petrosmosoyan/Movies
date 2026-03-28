package com.example.movies.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.FragmentDetailsBinding
import com.example.movies.domain.model.Details
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var binding: FragmentDetailsBinding? = null
    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        binding?.run {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            favorite.setOnClickListener {
                viewModel.toggleFavorite()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailsUIState.collect { uiState ->
                    updateUI(uiState.details ?: return@collect)
                }
            }
        }
    }

    private fun updateUI(details: Details) {
        binding?.run {
            val ratingValue = details.voteAverage ?: 0.0
            val rating = "★ ${String.format(Locale.US, "%.1f", ratingValue)} / 10"
            val budget = "$${details.budget ?: ""}"
            val revenue = "$${details.revenue ?: ""}"

            Glide.with(requireContext()).load(details.backdropPath).into(backdropImage)
            Glide.with(requireContext()).load(details.posterPath).into(posterImage)
            movieTitle.text = details.title
            releaseDate.text = details.releaseDate
            ratingText.text = rating
            overviewText.text = details.overview
            budgetText.text = budget
            revenueText.text = revenue

            val favoriteIcon = if (details.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
            favorite.setImageResource(favoriteIcon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}