package com.example.homework_17.di

import com.example.homework_17.data.log_in.LoginRepositoryImpl
import com.example.homework_17.data.log_in.service.LoginService
import com.example.homework_17.data.register.RegisterRepositoryImpl
import com.example.homework_17.data.register.service.RegisterService
import com.example.homework_17.domain.log_in.LoginRepository
import com.example.homework_17.domain.register.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideLoginRepository(loginService: LoginService): LoginRepository {
        return LoginRepositoryImpl(loginService)
    }

    @Singleton
    @Provides
    fun provideRegisterRepository(registerService: RegisterService): RegisterRepository {
        return RegisterRepositoryImpl(registerService)
    }

}