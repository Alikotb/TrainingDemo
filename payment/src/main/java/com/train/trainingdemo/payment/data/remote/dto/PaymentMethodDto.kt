package com.train.trainingdemo.payment.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PaymentMethodDto(
    val id: String,
    val type: String,
    val lastFour: String? = null,
    val expiryDate: String? = null,
    val cardType: String? = null
)
