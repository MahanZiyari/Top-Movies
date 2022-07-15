package mahan.topmovies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import dagger.hilt.android.AndroidEntryPoint
import mahan.topmovies.databinding.FragmentHomeBinding
import mahan.topmovies.ui.home.adapters.GenresAdapter
import mahan.topmovies.ui.home.adapters.TopMoviesAdapter
import mahan.topmovies.utils.initialize
import mahan.topmovies.viewmodel.HomeViewModel
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var topMoviesAdapter: TopMoviesAdapter

    @Inject
    lateinit var genresAdapter: GenresAdapter

    // Other
    val viewModel by viewModels<HomeViewModel>()

    // SnapPager helper for scrolling only one item in RecyclerView
    private val pagerSnapHelper by lazy { PagerSnapHelper() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadTopMoviesList(3)
        viewModel.loadGenresList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            // Get Top Movies
            viewModel.topMoviesList.observe(viewLifecycleOwner) {
                topMoviesAdapter.differ.submitList(it.data)
                topMoviesRecycler.initialize(
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    adapter = topMoviesAdapter
                )
                pagerSnapHelper.attachToRecyclerView(topMoviesRecycler)
                // Indicator
                topMoviesIndicator.attachToRecyclerView(topMoviesRecycler, pagerSnapHelper)
            }

            viewModel.genresList.observe(viewLifecycleOwner){
                genresAdapter.differ.submitList(it)
                genresRecycler.initialize(
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    adapter = genresAdapter
                )
            }
        }
    }
}