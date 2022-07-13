package mahan.topmovies.api

import mahan.topmovies.model.register.BodyRegister
import mahan.topmovies.model.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {
    // Responsible for registering user in server
    @POST("register")
    suspend fun registerUser(@Body body: BodyRegister): Response<ResponseRegister>
}