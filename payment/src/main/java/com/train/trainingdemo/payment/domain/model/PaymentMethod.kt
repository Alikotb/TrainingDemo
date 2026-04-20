package com.train.trainingdemo.payment.domain.model

sealed class PaymentMethod {
    data class Card(
        val id: String,
        val lastFourDigits: String,
        val expiryDate: String,
        val cardType: CardType
    ) : PaymentMethod()

    object AddNewCard : PaymentMethod()
    object Cash : PaymentMethod()
}

enum class CardType {
    MASTERCARD, VISA, AMEX, UNKNOWN
}
