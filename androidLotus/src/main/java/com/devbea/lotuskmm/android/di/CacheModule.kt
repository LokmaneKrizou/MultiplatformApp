package com.devbea.lotuskmm.android.di

import android.content.Context
import com.devbea.lotuskmm.android.BaseApplication
import com.devbea.lotuskmm.datasource.cache.*
import com.devbea.lotuskmm.domain.util.DatetimeUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDriverFactory(context: BaseApplication): DriverFactory =
        DriverFactory(context)

    @Singleton
    @Provides
    fun provideRecipeDatabase(driverFactory: DriverFactory): RecipeDatabase =
        RecipeDatabaseFactory(driverFactory).createDatabase()

    @Singleton
    @Provides
    fun provideRecipeCache(
        recipeDatabase: RecipeDatabase,
        datetimeUtil: DatetimeUtil
    ): RecipeCache =
        RecipeCacheImpl(recipeDatabase, datetimeUtil)
}