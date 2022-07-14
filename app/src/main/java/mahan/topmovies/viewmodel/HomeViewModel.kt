package mahan.topmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mahan.topmovies.model.home.ResponseMoviesList
import mahan.topmovies.repository.HomeRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {

    val topMoviesList = MutableLiveData<ResponseMoviesList>()

    fun loadTopMoviesList(id: Int) = viewModelScope.launch {
        val response = repository.topMoviesList(id)
        if (response.isSuccessful) {
            topMoviesList.postValue(response.body())
        }
    }
}