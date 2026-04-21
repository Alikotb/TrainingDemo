package com.train.trainingdemo.presentation.contract


interface HomeContract {
    sealed class Intent {
        object NavigateToMap : Intent()
        object NavigateToBiometric : Intent()
        object NavigateToPayment : Intent()
        object NavigateToMLKit : Intent()
//        object SaveFirstTime : Intent()

    }

    sealed interface Effect {
        object NavigateToMap : Effect
        object NavigateToBiometric : Effect
        object NavigateToPayment : Effect
        object NavigateToMLKit : Effect
    }
}