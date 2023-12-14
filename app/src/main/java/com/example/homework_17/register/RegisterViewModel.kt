package com.example.homework_17.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.helper.JsonConverter
import com.example.homework_17.common.ErrorHandling
import com.example.homework_17.common.Resource
import com.example.homework_17.dto.AuthDto
import com.example.homework_17.model.User
import com.example.homework_17.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    private var _userFlow = MutableStateFlow<Resource<User>>(Resource.Idle)
    val userFlow: SharedFlow<Resource<User>> = _userFlow.asStateFlow()

    fun register(authDto: AuthDto){
        viewModelScope.launch {
            _userFlow.value = Resource.Loading
            try {
                val response = Network.registerService().login(authDto)
                if(response.isSuccessful){
                    _userFlow.value = Resource.Success(response.body()!!)
                }else{
                    val error = JsonConverter.readErrorMessage(response.errorBody()?.string() ?: "")
                    _userFlow.value = Resource.Error(error)
                }

            }catch (e: Throwable){
                _userFlow.value = Resource.Error(ErrorHandling.NO_INTERNET_CONNECTION.message)
            }finally {
                _userFlow.value = Resource.Idle
            }
        }
    }

}