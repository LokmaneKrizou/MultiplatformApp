package com.devbea.lotuskmm.datasource.cache

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(RecipeDatabase.Schema, DB_NAME)
    }

    companion object {
        private const val DB_NAME = "recipes.db"
    }
}