package com.devbea.lotuskmm.android.di

import android.content.Context
import com.devbea.lotuskmm.android.BaseApplication
import com.devbea.lotuskmm.domain.util.DatetimeUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication

    }

    @Singleton
    @Provides
    fun provideDateTimeUtil(): DatetimeUtil = DatetimeUtil()
}