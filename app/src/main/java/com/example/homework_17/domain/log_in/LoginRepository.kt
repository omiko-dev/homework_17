package com.example.homework_17.domain.log_in

import com.example.homework_17.common.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun logIn(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>
}