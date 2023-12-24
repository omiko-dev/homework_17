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

enum class ErrorMessage(val message: String){
    ENTER_ALL_FIELD("Enter all field"),
    ENTER_CORRECT_EMAIL("Enter Correct Email"),
    ENTER_CORRECT_PASSWORD("You have to enter at least 6 digit in password"),
    ENTER_CORRECT_REPEAT_PASSWORD("Password and Repeat Password is not same")
}
