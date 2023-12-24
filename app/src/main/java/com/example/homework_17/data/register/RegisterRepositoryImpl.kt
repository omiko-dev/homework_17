package com.example.homework_17.data.register

import com.example.homework_17.common.ErrorHandling
import com.example.homework_17.common.Resource
import com.example.homework_17.data.register.mapper.toDomain
import com.example.homework_17.domain.register.RegisterRepository
import com.example.homework_17.domain.register.RegisterResponse
import com.example.homework_17.data.helper.JsonConverter
import com.example.homework_17.data.register.service.RegisterService
import com.example.homework_17.domain.register.RegisterRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerService: RegisterService) :
    RegisterRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<Resource<RegisterResponse>> {
        return flow {
            try {
                emit(Resource.Loading)
                val response = registerService.register(registerRequest)
                if (response.isSuccessful) {
                    emit(Resource.Success(successData = response.body()!!.toDomain()))
                } else {
                    val error = JsonConverter.readErrorMessage(response.errorBody()?.string() ?: "")
                    emit(Resource.Error(error))
                }
            } catch (e: Throwable) {
                emit(Resource.Error(ErrorHandling.NO_INTERNET_CONNECTION.message))
            } finally {
                emit(Resource.Idle)
            }
        }
    }
}