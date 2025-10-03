package com.test.domain

import com.test.domain.usecase.GetPropertiesUseCaseImpl
import com.test.domain.repository.PropertiesRepository
import com.test.domain.usecase.GetPropertiesUseCase
import com.test.domain.usecase.GetPropertyUseCase
import com.test.domain.usecase.GetPropertyUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideGetPropertiesUseCase(
        propertiesRepository: PropertiesRepository
    ): GetPropertiesUseCase {
        return GetPropertiesUseCaseImpl(propertiesRepository)
    }

    @Singleton
    @Provides
    fun provideGetPropertyUseCase(
        propertiesRepository: PropertiesRepository
    ): GetPropertyUseCase {
        return GetPropertyUseCaseImpl(propertiesRepository)
    }
}