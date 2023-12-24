package com.example.homework_17.data.log_in

import com.example.homework_17.common.ErrorHandling
import com.example.homework_17.common.Resource
import com.example.homework_17.data.helper.JsonConverter
import com.example.homework_17.data.log_in.mapper.toDomain
import com.example.homework_17.data.log_in.service.LoginService
import com.example.homework_17.domain.log_in.LoginRepository
import com.example.homework_17.domain.log_in.LoginRequest
import com.example.homework_17.domain.log_in.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginService: LoginService) : LoginRepository {
    override suspend fun logIn(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> {
        return flow {
            try {
                val response = loginService.login(loginRequest)
                emit(Resource.Loading)
                if (response.isSuccessful) {
                    emit(Resource.Success(successData = response.body()!!.toDomain()))
                } else {
                    val error = JsonConverter.readErrorMessage(response.errorBody()?.string() ?: "")
                    emit(Resource.Error(error))
                }
            } catch (e: UnknownHostException) {
                emit(Resource.Error(ErrorHandling.NO_INTERNET_CONNECTION.message))
            } finally {
                emit(Resource.Idle)
            }
        }
    }
}