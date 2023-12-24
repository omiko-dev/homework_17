package com.example.homework_17.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.domain.datastore_proto.DataStoreRepository
import com.example.homework_17.domain.datastore_proto.DataStoreResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository): ViewModel() {

    private var _dataStoreState = MutableSharedFlow<DataStoreResponse>()
    val dataStoreState = _dataStoreState.asSharedFlow()

    fun clearUserData(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.clearData()
        }
    }

    fun getUserData() {
        viewModelScope.launch {
            dataStoreRepository.getData().collect {
                _dataStoreState.emit(it)
            }
        }
    }
}