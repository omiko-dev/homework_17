package com.example.homework_17.data.register.service

import com.example.homework_17.domain.register.RegisterRequest
import com.example.homework_17.data.register.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegisterService {
    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponseDto>
}
