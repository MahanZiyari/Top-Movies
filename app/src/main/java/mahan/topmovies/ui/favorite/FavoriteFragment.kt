package mahan.topmovies.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mahan.topmovies.databinding.FragmentFavoriteBinding
import mahan.topmovies.ui.home.HomeFragmentDirections
import mahan.topmovies.utils.initialize
import mahan.topmovies.utils.showInvisible
import mahan.topmovies.viewmodel.FavoriteViewModel
import javax.inject.Inject


@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    //Binding
    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    //Other
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // InitViews
        binding.apply {
            //Show all favorite
            viewModel.loadFavoriteList()
            //List
            viewModel.favoriteList.observe(viewLifecycleOwner) {
                favoriteAdapter.setData(it)
                favoriteRecycler.initialize(
                    layoutManager = LinearLayoutManager(requireContext()),
                    adapter = favoriteAdapter
                )
            }
            //Empty
            viewModel.empty.observe(viewLifecycleOwner) {
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    favoriteRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    favoriteRecycler.showInvisible(true)
                }
            }

            // On Click
            favoriteAdapter.setOnItemClickListener {
                val direction = HomeFragmentDirections.actionToDefault(it.id)
                findNavController().navigate(direction)
            }
        }
    }
}