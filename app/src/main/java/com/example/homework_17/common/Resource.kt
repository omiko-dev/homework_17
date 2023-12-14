package com.example.homework_17.common


sealed class Resource<out R> {
    data class Success<out R>(val successData: R): Resource<R>()
    data class Error(val error: String?): Resource<Nothing>()
    data object Loading: Resource<Nothing>()
    data object Idle: Resource<Nothing>()
}

data class ErrorResponse(val error: String?)

enum class ErrorHandling(val message: String){
    NO_INTERNET_CONNECTION("no internet connection!")
}