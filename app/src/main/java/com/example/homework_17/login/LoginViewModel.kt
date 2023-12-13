package com.example.homework_17.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.common.ErrorResponse
import com.example.homework_17.common.Resource
import com.example.homework_17.dto.AuthDto
import com.example.homework_17.model.User
import com.example.homework_17.network.Network
import com.example.homework_17.session.SessionData
import com.example.homework_17.session.SessionManager
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class LoginViewModel : ViewModel() {

    private val _userFlow = MutableStateFlow<Resource<User>>(Resource.Idle)
    val userFlow: SharedFlow<Resource<User>> = _userFlow.asStateFlow()

    private val moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
    private val adapter by lazy {
        moshi.adapter(ErrorResponse::class.java)
    }

    fun login(authDto: AuthDto) {
        viewModelScope.launch {
            _userFlow.value = Resource.Loading
            try {
                val response = Network.loginService().login(authDto)
                if (response.isSuccessful) {
                    _userFlow.value = Resource.Success(response.body()!!)
                } else {
                    val error = readErrorMessage(response.errorBody()?.string() ?: "")
                    _userFlow.value = Resource.Error(error)
                }

            } catch (e: SocketTimeoutException) {
                Log.i("exception", "${e.message}")
            }
            _userFlow.value = Resource.Idle
        }
    }

    fun saveSessionData(sessionData: SessionData) {
        viewModelScope.launch {
            SessionManager.saveSessionData(sessionData)
        }
    }

    private fun readErrorMessage(errorBody: String): String? {
        return try {
            val errorResponse = adapter.fromJson(errorBody)
            errorResponse?.error
        }catch (e: JsonDataException){
            Log.e("JsonDataException", "JSON parsing error")
            null
        }
    }
}
