package com.example.homework_17.dto

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class AuthDto(
    @Json(name = "email")
    val email: String,

    @Json(name = "password")
    val password: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthDto> {
        override fun createFromParcel(parcel: Parcel): AuthDto {
            return AuthDto(parcel)
        }

        override fun newArray(size: Int): Array<AuthDto?> {
            return arrayOfNulls(size)
        }
    }
}

