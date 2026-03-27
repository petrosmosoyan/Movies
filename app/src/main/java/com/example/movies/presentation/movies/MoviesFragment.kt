package com.example.movies.presentation.movies

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movies.R
import com.example.movies.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private var binding: FragmentMoviesBinding? = null
    private val viewModel: MoviesViewModel by viewModels()

    private val adapter = MoviesAdapter(
        onItemClick = { id -> navigateToDetails(id) },
        onFavoriteClick = { movieId -> viewModel.toggleFavorite(movieId) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesBinding.bind(view)


        ViewCompat.setOnApplyWindowInsetsListener(binding?.rootLyt ?: return) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        binding?.run {
            recyclerView.adapter = adapter
            swipeView.setOnRefreshListener { viewModel.refreshMovies() }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesUIState.collect {
                    binding?.swipeView?.isRefreshing = it.isLoading
                    adapter.submitList(it.movies)
                }
            }
        }
    }

    private fun navigateToDetails(id: Int) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}