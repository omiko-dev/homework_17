package com.example.homework_17.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.datastore.UserDataSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    fun clearUserData(){
        viewModelScope.launch(Dispatchers.IO) {
            UserDataSerializer.clearUserData()
        }
    }

}