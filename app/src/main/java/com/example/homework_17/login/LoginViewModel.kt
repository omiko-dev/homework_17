package com.example.homework_17.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.helper.JsonConverter
import com.example.homework_17.common.ErrorHandling
import com.example.homework_17.common.Resource
import com.example.homework_17.datastore.SessionUserData
import com.example.homework_17.datastore.UserDataSerializer
import com.example.homework_17.dto.AuthDto
import com.example.homework_17.model.User
import com.example.homework_17.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class LoginViewModel : ViewModel() {

    private val _userFlow = MutableStateFlow<Resource<User>>(Resource.Idle)
    val userFlow: SharedFlow<Resource<User>> = _userFlow.asStateFlow()



    fun login(authDto: AuthDto) {
        viewModelScope.launch {
            _userFlow.value = Resource.Loading
            try {
                val response = Network.loginService().login(authDto)
                if (response.isSuccessful) {
                    _userFlow.value = Resource.Success(response.body()!!)
                } else {
                    val error = JsonConverter.readErrorMessage(response.errorBody()?.string() ?: "")
                    _userFlow.value = Resource.Error(error)
                }

            } catch (e: UnknownHostException) {
                _userFlow.value = Resource.Error(ErrorHandling.NO_INTERNET_CONNECTION.message)
            } finally {
                _userFlow.value = Resource.Idle
            }
        }
    }

    fun saveUserData(sessionUserData: SessionUserData) {
        viewModelScope.launch(Dispatchers.IO) {
            UserDataSerializer.setUserData(sessionUserData)
        }
    }


}
