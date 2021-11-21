package com.devbea.lotuskmm.android.di

import com.devbea.lotuskmm.datasource.network.KtorClientFactory
import com.devbea.lotuskmm.datasource.network.RecipeService
import com.devbea.lotuskmm.datasource.network.RecipeServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient = KtorClientFactory().build()

    @Singleton
    @Provides
    fun provideRecipeService(httpClient: HttpClient): RecipeService = RecipeServiceImpl(httpClient)


}