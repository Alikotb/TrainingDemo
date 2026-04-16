package com.train.trainingdemo.navigation
import kotlinx.serialization.Serializable

sealed class AppRoute {
    @Serializable
    object SplashRoute : AppRoute()


    @Serializable
    object HomeRoute : AppRoute()
    @Serializable
    object MapRoute : AppRoute()

}