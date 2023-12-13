package com.example.homework_17.service

import com.example.homework_17.dto.AuthDto
import com.example.homework_17.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(@Body authDto: AuthDto) : Response<User>
}

interface RegisterService {
    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun login(@Body authDto: AuthDto) : Response<User>
}
