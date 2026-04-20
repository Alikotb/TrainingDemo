package com.train.trainingdemo.navigation
import kotlinx.serialization.Serializable

sealed class AppRoute {
    @Serializable
    object SplashRoute : AppRoute()


    @Serializable
    object HomeRoute : AppRoute()
    @Serializable
    object MapRoute : AppRoute()
    @Serializable
    object BiometricHome : AppRoute()
    @Serializable
    object PatternLock : AppRoute()
    @Serializable
    object FingerPoint : AppRoute()
    @Serializable
    object Payment : AppRoute()

}