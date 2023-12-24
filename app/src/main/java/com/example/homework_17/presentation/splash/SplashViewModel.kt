package com.example.homework_17.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.domain.datastore_proto.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) : ViewModel() {
    private var _userModelFlow = MutableSharedFlow<NavigationFromSplashEvent>()
    val userModelFlow = _userModelFlow.asSharedFlow()

    init {
        navigation()
    }

    private fun navigation() {
        viewModelScope.launch {
            dataStoreRepository.getData().collect {
                when(it.inLogin) {
                    true -> _userModelFlow.emit(NavigationFromSplashEvent.NavigationToHome)
                    false -> _userModelFlow.emit(NavigationFromSplashEvent.NavigationToLogin)
                }
            }
        }
    }
}