package mahan.topmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mahan.topmovies.model.home.ResponseMoviesList
import mahan.topmovies.repository.SearchRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    val movies = MutableLiveData<ResponseMoviesList>()
    val loading = MutableLiveData<Boolean>()
    val empty = MutableLiveData<Boolean>()

    fun searchMoviesByName(name: String) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.searchMovies(name)
        if (response.isSuccessful) {
            if (response.body()?.data!!.isNotEmpty()) {
                movies.postValue(response.body())
                empty.postValue(false)
            } else {
                empty.postValue(true)
            }
        }
        loading.postValue(false)
    }
}