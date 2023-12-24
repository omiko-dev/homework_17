package com.example.homework_17.presentation.splash

sealed class NavigationFromSplashEvent {
    data object NavigationToHome: NavigationFromSplashEvent()
    data object NavigationToLogin: NavigationFromSplashEvent()
}
