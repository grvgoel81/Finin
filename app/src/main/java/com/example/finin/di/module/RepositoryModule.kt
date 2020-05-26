package com.example.finin.di.module

import com.example.finin.api.UserAPI
import com.example.finin.repository.UserRepository
import com.example.finin.repository.UserRepositoryImpl
import com.example.finin.utils.GetUserDataImpl
import com.example.finin.utils.GetUsersData
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideNewsUseCase(userRepository: UserRepository): GetUsersData {
        return GetUserDataImpl(userRepository)
    }

    @Provides
    fun provideNewsRepository(userAPI: UserAPI): UserRepository {
        return UserRepositoryImpl(userAPI)
    }
}