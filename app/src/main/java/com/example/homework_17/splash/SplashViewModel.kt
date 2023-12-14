package com.example.homework_17.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.datastore.UserDataSerializer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private var _userModelFlow = MutableSharedFlow<Boolean>()
    val userModelFlow = _userModelFlow.asSharedFlow()

    init {
        test()
    }

    private fun test() {
        viewModelScope.launch {
            UserDataSerializer.userDataFlow.collect {
                delay(1000)
                _userModelFlow.emit(it.isLogIn)
            }
        }
    }
}