package com.example.homework_17.presentation.login

import com.example.homework_17.domain.log_in.LoginRequest

sealed class LogInEvent {
    data class LogIn(val loginRequest: LoginRequest): LogInEvent()
}
