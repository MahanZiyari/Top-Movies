package mahan.topmovies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import mahan.topmovies.R
import mahan.topmovies.databinding.FragmentDetailBinding
import mahan.topmovies.db.MovieEntity
import mahan.topmovies.utils.initialize
import mahan.topmovies.utils.showInvisible
import mahan.topmovies.viewmodel.DetailViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var imageAdapter: ImageAdapter

    @Inject
    lateinit var entity: MovieEntity

    // Other
    private var movieId = 0
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = args.movieId
        if (movieId > 0)
            viewModel.loadDetailMovie(movieId)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //Load data
            viewModel.detailMovie.observe(viewLifecycleOwner) { response ->
                posterBigImg.load(response.poster)
                posterNormalImg.load(response.poster) {
                    crossfade(true)
                    crossfade(800)
                }
                movieNameTxt.text = response.title
                movieRateTxt.text = response.imdbRating
                movieTimeTxt.text = response.runtime
                movieDateTxt.text = response.released
                movieSummaryInfo.text = response.plot
                movieActorsInfo.text = response.actors
                //Images Adapter
                imageAdapter.differ.submitList(response.images)
                imagesRecyclerView.initialize(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    imageAdapter
                )
                //Fav click
                favImg.setOnClickListener {
                    entity.id = movieId
                    entity.poster = response.poster.toString()
                    entity.title = response.title.toString()
                    entity.rate = response.rated.toString()
                    entity.country = response.country.toString()
                    entity.year = response.year.toString()
                    viewModel.favoriteMovie(movieId, entity)
                }
            }
            //Loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    detailLoading.showInvisible(true)
                    detailScrollView.showInvisible(false)
                } else {
                    detailLoading.showInvisible(false)
                    detailScrollView.showInvisible(true)
                }
            }
            //Default fav icon color
            lifecycleScope.launchWhenCreated {
                if (viewModel.existsMovie(movieId)) {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                } else {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(),
                        R.color.philippineSilver))
                }
            }
            //Change image with click
            viewModel.isFavorite.observe(viewLifecycleOwner) {
                if (it) {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                } else {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(),
                        R.color.philippineSilver))
                }
            }
            //Back
            backImg.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

}