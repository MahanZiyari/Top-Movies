package mahan.topmovies.db

import MoviesDao
import academy.nouri.s1_project.db.MovieEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
}