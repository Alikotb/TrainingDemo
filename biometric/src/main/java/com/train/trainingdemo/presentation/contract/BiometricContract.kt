package com.train.trainingdemo.presentation.contract


interface BiometricContract {
    sealed class Intent {
        object NavigatePatternLock: Intent()
        object NavigateFingerPoint: Intent()
//        object SaveFirstTime : Intent()

    }

    sealed interface Effect {
        object NavigatePatternLock : Effect
        object NavigateFingerPoint : Effect
    }
}