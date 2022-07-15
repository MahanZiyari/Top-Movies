package mahan.topmovies.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import mahan.topmovies.databinding.ItemHomeMoviesLastBinding
import mahan.topmovies.model.home.ResponseMoviesList.Data
import javax.inject.Inject

class LastMoviesAdapter @Inject constructor() : RecyclerView.Adapter<LastMoviesAdapter.ViewHolder>() {

    private lateinit var binding: ItemHomeMoviesLastBinding
    private var movies = emptyList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastMoviesAdapter.ViewHolder {
        binding =
            ItemHomeMoviesLastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: LastMoviesAdapter.ViewHolder, position: Int) {
        holder.bindModelToUi(movies[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindModelToUi(item: Data) {
            binding.apply {
                movieNameTxt.text = item.title
                movieCountryTxt.text = item.country
                movieYearTxt.text = item.year
                movieRateTxt.text = item.imdbRating
                moviePosterImg.load(item.poster) {
                    crossfade(true)
                    crossfade(800)
                }
            }
        }
    }

    fun submitNewList(movies: List<Data>) {
        val moviesDiffUtil = MoviesDiffUtil(newItem = movies, oldItem = this.movies)
        val diffUtil = DiffUtil.calculateDiff(moviesDiffUtil)
        this.movies = movies
        diffUtil.dispatchUpdatesTo(this)
    }

    class MoviesDiffUtil(private val oldItem: List<Data>, private val newItem: List<Data>): DiffUtil.Callback(){
        override fun getOldListSize(): Int = oldItem.size

        override fun getNewListSize(): Int = newItem.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

    }
}