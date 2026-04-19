package com.train.trainingdemo.presentation.contract


interface BiometricContract {
    sealed class Intent {
        object NavigatePatternLock: Intent()
//        object SaveFirstTime : Intent()

    }

    sealed interface Effect {
        object NavigatePatternLock : Effect
    }
}