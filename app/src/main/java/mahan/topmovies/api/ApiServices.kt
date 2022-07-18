package mahan.topmovies.api

import mahan.topmovies.model.home.ResponseGenresList
import mahan.topmovies.model.home.ResponseMoviesList
import mahan.topmovies.model.register.BodyRegister
import mahan.topmovies.model.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    // Responsible for registering user in server
    @POST("register")
    suspend fun registerUser(@Body body: BodyRegister): Response<ResponseRegister>

    @GET("genres/{genre_id}/movies")
    suspend fun moviesTopList(@Path("genre_id") id: Int): Response<ResponseMoviesList>

    @GET("genres")
    suspend fun genresList(): Response<ResponseGenresList>

    @GET("movies")
    suspend fun moviesLastList(): Response<ResponseMoviesList>

    @GET("movies")
    suspend fun searchMovie(@Query("q") name: String): Response<ResponseMoviesList>


}