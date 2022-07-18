package mahan.topmovies.repository

import mahan.topmovies.api.ApiServices
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: ApiServices) {
    suspend fun searchMovies(name: String) = api.searchMovie(name)
}