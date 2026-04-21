package com.train.trainingdemo.presentation.contract


interface MLKitContract {
    sealed class Intent {
        object NavigateToTranslator : Intent()
//        object SaveFirstTime : Intent()

    }

    sealed interface Effect {
        object NavigateToTranslator  : Effect
    }
}