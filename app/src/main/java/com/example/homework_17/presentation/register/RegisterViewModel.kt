package com.example.homework_17.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.common.Resource
import com.example.homework_17.domain.register.RegisterRequest
import com.example.homework_17.domain.register.RegisterRepository
import com.example.homework_17.domain.register.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerRepository: RegisterRepository): ViewModel() {

    private var _userFlow = MutableStateFlow<Resource<RegisterResponse>>(Resource.Idle)
    val userFlow: SharedFlow<Resource<RegisterResponse>> = _userFlow.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when(event) {
            is RegisterEvent.Register -> register(event.registerRequest)
        }
    }

    private fun register(registerRequest: RegisterRequest){
        viewModelScope.launch {
            registerRepository.register(registerRequest).collect {
                when(it){
                    is Resource.Loading -> {
                        _userFlow.value = Resource.Loading
                    }
                    is Resource.Success -> {
                        _userFlow.value = Resource.Success(it.successData)
                    }
                    is Resource.Error -> {
                        _userFlow.value = Resource.Error(it.error)
                    }
                    is Resource.Idle -> {
                        _userFlow.value = Resource.Idle
                    }
                }
            }
        }
    }

}