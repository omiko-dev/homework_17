package com.example.homework_17.session

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SessionManager {
    private const val PREF_NAME = "session_pref"
    private const val IS_LOGGED_IN_KEY = "is_logged_in"
    private const val EMAIL_KEY = "email"

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveSessionData(sessionData: SessionData) {
        sharedPreferences.edit {
            putBoolean(IS_LOGGED_IN_KEY, sessionData.isLoggedIn)
            putString(EMAIL_KEY, sessionData.email)
        }
    }

    fun clearSessionData() {
        sharedPreferences.edit {
            putBoolean(IS_LOGGED_IN_KEY, false)
            putString(EMAIL_KEY, "")
        }
    }

    fun getSessionData(): SessionData {
        return SessionData(
            isLoggedIn = sharedPreferences.getBoolean(IS_LOGGED_IN_KEY, false),
            email = sharedPreferences.getString(EMAIL_KEY, "") ?: ""
        )
    }
}
