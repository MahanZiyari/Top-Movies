package mahan.topmovies.api

import mahan.topmovies.model.home.ResponseGenresList
import mahan.topmovies.model.home.ResponseMoviesList
import mahan.topmovies.model.register.BodyRegister
import mahan.topmovies.model.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {
    // Responsible for registering user in server
    @POST("register")
    suspend fun registerUser(@Body body: BodyRegister): Response<ResponseRegister>

    @GET("genres/{genre_id}/movies")
    suspend fun moviesTopList(@Path("genre_id") id: Int): Response<ResponseMoviesList>

    @GET("genres")
    suspend fun genresList(): Response<ResponseGenresList>

}