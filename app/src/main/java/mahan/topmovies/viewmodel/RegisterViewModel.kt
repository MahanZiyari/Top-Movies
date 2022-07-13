package mahan.topmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mahan.topmovies.model.register.BodyRegister
import mahan.topmovies.model.register.ResponseRegister
import mahan.topmovies.repository.RegisterRepository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository): ViewModel() {
    val registerUser = MutableLiveData<ResponseRegister>()
    val loading = MutableLiveData<Boolean>()

    fun sendRegisterUser(body: BodyRegister) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.registerUser(body)
        if (response.isSuccessful) {
            registerUser.postValue(response.body())
        }
        loading.postValue(false)
    }
}