package com.train.trainingdemo.presentation.contract


interface HomeContract {
    sealed class Intent {
        object NavigateToMap : Intent()
//        object SaveFirstTime : Intent()

    }

    sealed interface Effect {
        object NavigateToMap : Effect
    }
}