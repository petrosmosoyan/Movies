package com.example.movies.presentation.movies

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
            val footerAdapter = MoviesLoadStateAdapter { adapter.retry() }
            recyclerView.adapter = adapter.withLoadStateFooter(footer = footerAdapter)

            (recyclerView.layoutManager as? GridLayoutManager)?.let { layoutManager ->
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position == adapter.itemCount && footerAdapter.itemCount > 0) {
                            layoutManager.spanCount
                        } else {
                            1
                        }
                    }
                }
            }

            swipeView.setOnRefreshListener { adapter.refresh() }
            retryButton.setOnClickListener { adapter.retry() }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.moviesPagingData.collectLatest {
                        adapter.submitData(it)
                    }
                }

                launch {
                    adapter.loadStateFlow.collect { loadStates ->
                        val isRefreshing = loadStates.refresh is LoadState.Loading
                        val isRefreshError = loadStates.refresh is LoadState.Error
                        val isEmpty = loadStates.refresh is LoadState.NotLoading && adapter.itemCount == 0

                        binding?.run {
                            swipeView.isRefreshing = isRefreshing
                            errorLayout.isVisible = isRefreshError && adapter.itemCount == 0
                            emptyText.isVisible = isEmpty
                            
                            if (isRefreshError && adapter.itemCount == 0) {
                                val error = (loadStates.refresh as LoadState.Error).error
                                errorText.text = error.localizedMessage ?: "Network Error"
                            }
                        }
                    }
                }
            }
        }
    }

    private fun navigateToDetails(id: Int) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}