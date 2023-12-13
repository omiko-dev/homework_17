package com.example.homework_17.model

import com.squareup.moshi.Json

data class User(
    @Json(name = "id") val id: String? = null,
    @Json(name = "token") val token: String? = null
)

enum class ErrorMessage(val message: String){
    ENTER_ALL_FIELD("Enter all field"),
    ENTER_CORRECT_EMAIL("Enter Correct Email"),
    ENTER_CORRECT_PASSWORD("You have to enter at least 6 digit in password"),
    ENTER_CORRECT_REPEAT_PASSWORD("Password and Repeat Password is not same")
}
