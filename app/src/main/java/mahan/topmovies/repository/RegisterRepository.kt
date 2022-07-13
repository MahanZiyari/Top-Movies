package mahan.topmovies.repository

import mahan.topmovies.api.ApiServices
import mahan.topmovies.model.register.BodyRegister
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val apiServices: ApiServices) {
    suspend fun registerUser(body: BodyRegister) = apiServices.registerUser(body)
}