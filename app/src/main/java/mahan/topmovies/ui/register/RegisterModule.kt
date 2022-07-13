package mahan.topmovies.ui.register


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mahan.topmovies.model.register.BodyRegister
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {

    @Provides
    @Singleton
    fun provideUserBody() = BodyRegister()
}