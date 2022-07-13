package mahan.topmovies.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mahan.topmovies.api.ApiServices
import mahan.topmovies.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideConnectionTimeOut(): Long = Constants.CONNECTION_TIME

    @Provides
    @Singleton
    fun provideGsonConfig(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideClient(connectionTimeOut: Long, interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .writeTimeout(connectionTimeOut, TimeUnit.SECONDS)
            .readTimeout(connectionTimeOut, TimeUnit.SECONDS)
            .connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, baseUrl: String, gson: Gson): ApiServices =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .baseUrl(baseUrl)
            .build()
            .create(ApiServices::class.java)
}