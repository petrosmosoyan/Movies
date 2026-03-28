package com.example.movies.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.movies.R
import com.example.movies.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var binding: FragmentFavoritesBinding? = null
    private val viewModel: FavoritesViewModel by viewModels()

    private val adapter = FavoritesAdapter(onItemClick = { movieId ->
        navigateToDetails(movieId)
    }, onDeleteClick = { movieId ->
        viewModel.toggleFavorite(movieId)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFavoritesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            ViewCompat.setOnApplyWindowInsetsListener(recyclerView) { v, insets ->
                val innerTop = resources.getDimensionPixelSize(R.dimen.padding_12)
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top + innerTop, systemBars.right, 0)
                insets
            }

            recyclerView.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteUIState.collect { state ->
                    adapter.submitList(state.favorites)
                }
            }
        }
    }

    private fun navigateToDetails(movieId: Int) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(movieId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}