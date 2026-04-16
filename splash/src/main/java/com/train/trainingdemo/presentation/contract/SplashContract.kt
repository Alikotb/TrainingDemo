package com.train.trainingdemo.presentation.contract


interface SplashContract {
    sealed class Intent {
        object NavigateToNext : Intent()
        object SaveFirstTime : Intent()

    }

    sealed interface Effect {
        object NavigateToHome : Effect
        object NavigateToOnBoarding : Effect
    }
}