package com.example.homework_17.data.helper

import com.example.homework_17.common.ErrorResponse
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object JsonConverter {
    private val moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
    private val adapter by lazy {
        moshi.adapter(ErrorResponse::class.java)
    }


    fun readErrorMessage(errorBody: String): String? {
        return try {
            val errorResponse = adapter.fromJson(errorBody)
            errorResponse?.error
        } catch (e: JsonDataException) {
            null
        }
    }
}