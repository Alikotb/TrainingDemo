package com.train.trainingdemo.payment.data.mapper

import com.train.trainingdemo.payment.data.remote.dto.PaymentMethodDto
import com.train.trainingdemo.payment.domain.model.CardType
import com.train.trainingdemo.payment.domain.model.PaymentMethod

fun PaymentMethodDto.toDomain(): PaymentMethod {
    return when (type) {
        "card" -> PaymentMethod.Card(
            id = id,
            lastFourDigits = lastFour ?: "",
            expiryDate = expiryDate ?: "",
            cardType = try {
                CardType.valueOf(cardType?.uppercase() ?: "UNKNOWN")
            } catch (e: Exception) {
                CardType.UNKNOWN
            }
        )
        "cash" -> PaymentMethod.Cash
        else -> PaymentMethod.AddNewCard
    }
}
