package mahan.topmovies.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mahan.topmovies.databinding.FragmentSearchBinding
import mahan.topmovies.ui.home.HomeFragmentDirections
import mahan.topmovies.ui.home.adapters.LastMoviesAdapter
import mahan.topmovies.utils.initialize
import mahan.topmovies.utils.showInvisible
import mahan.topmovies.viewmodel.SearchViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    //Binding
    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var moviesAdapter: LastMoviesAdapter


    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // InitView
        binding.apply {
            // Response on user search
            searchEdt.addTextChangedListener {
                val searchQuery = it.toString()
                if (searchQuery.isNotEmpty()) {
                    viewModel.searchMoviesByName(searchQuery)
                }
            }
            //Get movies list
            viewModel.movies.observe(viewLifecycleOwner) {
                moviesAdapter.submitNewList(it.data)
                moviesRecycler.initialize(
                    layoutManager = LinearLayoutManager(requireContext()),
                    adapter = moviesAdapter
                )
            }

            //Loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    searchLoading.showInvisible(true)
                } else {
                    searchLoading.showInvisible(false)
                }
            }
            //Empty items
            viewModel.empty.observe(viewLifecycleOwner) {
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    moviesRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    moviesRecycler.showInvisible(true)
                }
            }

            // On Click
            moviesAdapter.setOnItemClickListener {
                val direction = HomeFragmentDirections.actionToDefault(it.id!!.toInt())
                findNavController().navigate(direction)
            }
        }
    }
}