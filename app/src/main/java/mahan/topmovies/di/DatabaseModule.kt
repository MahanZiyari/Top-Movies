package mahan.topmovies.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mahan.topmovies.db.MovieEntity
import mahan.topmovies.db.MoviesDatabase
import mahan.topmovies.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MoviesDatabase =
        Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            Constants.MOVIES_DATABASE
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(db: MoviesDatabase) = db.movieDao()

    @Provides
    @Singleton
    fun provideEntity(): MovieEntity = MovieEntity()
}