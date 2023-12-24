package com.example.homework_17.data.log_in.mapper

import com.example.homework_17.data.log_in.LoginResponseDto
import com.example.homework_17.domain.log_in.LoginResponse

fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(this.token)
}