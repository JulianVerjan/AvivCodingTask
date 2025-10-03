package com.test.data

import com.test.data.api.PropertiesApi
import com.test.data.repository.PropertiesRepositoryImpl
import com.test.domain.repository.PropertiesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providePropertiesRepository(
        propertiesApi: PropertiesApi
    ): PropertiesRepository {
        return PropertiesRepositoryImpl(propertiesApi)
    }

    @Singleton
    @Provides
    fun provideePropertiesApi(retrofit: Retrofit): PropertiesApi =
        retrofit.create(PropertiesApi::class.java)
}