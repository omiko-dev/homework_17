package com.example.homework_17.presentation.register

import com.example.homework_17.domain.register.RegisterRequest

sealed class RegisterEvent {
    data class Register(val registerRequest: RegisterRequest): RegisterEvent()
}
