package com.example.homework_17.domain.register

import com.example.homework_17.common.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(registerRequest: RegisterRequest): Flow<Resource<RegisterResponse>>
}