package com.devbea.lotuskmm.di

import com.devbea.lotuskmm.datasource.cache.*
import com.devbea.lotuskmm.domain.util.DatetimeUtil

class CacheModule {

    private val driverFactory: DriverFactory by lazy { DriverFactory() }
    val recipeDataBase: RecipeDatabase by lazy {
        RecipeDatabaseFactory(driverFactory = driverFactory).createDatabase()
    }
    val recipeCache: RecipeCache by lazy {
        RecipeCacheImpl(
            recipeDatabase = recipeDataBase,
            datetimeUtil = DatetimeUtil()
        )
    }

}

