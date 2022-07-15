package mahan.topmovies.repository

import mahan.topmovies.api.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiServices: ApiServices) {
    suspend fun topMoviesList(id: Int) = apiServices.moviesTopList(id)
    suspend fun genresList() = apiServices.genresList()
}