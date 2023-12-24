package com.example.homework_17.data.log_in.service

import com.example.homework_17.domain.log_in.LoginRequest
import com.example.homework_17.data.log_in.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponseDto>
}