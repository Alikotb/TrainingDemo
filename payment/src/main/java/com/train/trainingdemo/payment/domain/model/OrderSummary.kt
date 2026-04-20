package com.train.trainingdemo.payment.domain.model

data class OrderSummary(
    val subtotal: Double,
    val discount: Double,
    val deliveryFee: Double,
    val serviceFee: Double,
    val total: Double,
    val currency: String = "EGP"
)
