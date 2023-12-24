package com.example.homework_17.di

import android.content.Context
import com.example.homework_17.data.datastore.DataStoreRepositoryImpl
import com.example.homework_17.domain.datastore_proto.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Singleton
    @Provides
    fun provideDataStoreRepository(@ApplicationContext appContext: Context): DataStoreRepository =
        DataStoreRepositoryImpl(appContext)
}