package com.example.homework_17.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    _userFlow.value = Resource.Error(response.errorBody()?.string())
                }

            }catch (e: Throwable){
                Log.i("exception", e.message!!)
            }finally {
                _userFlow.value = Resource.Idle
            }
        }
    }

}