package com.example.homework_17.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.common.Resource
import com.example.homework_17.domain.datastore_proto.DataStoreRepository
import com.example.homework_17.domain.datastore_proto.DataStoreRequest
import com.example.homework_17.domain.log_in.LoginRepository
import com.example.homework_17.domain.log_in.LoginRequest
import com.example.homework_17.domain.log_in.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _userFlow = MutableStateFlow<Resource<LoginResponse>>(Resource.Idle)
    val userFlow: SharedFlow<Resource<LoginResponse>> = _userFlow.asStateFlow()

    fun onEvent(event: LogInEvent){
        when(event){
            is LogInEvent.LogIn -> login(event.loginRequest)
        }
    }

    private fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginRepository.logIn(loginRequest).collect {
                when (it) {
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

    fun setDataStore(dataStoreRequest: DataStoreRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.setData(dataStoreRequest)
        }
    }
}
