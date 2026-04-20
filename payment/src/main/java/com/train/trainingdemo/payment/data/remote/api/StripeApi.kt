package com.train.trainingdemo.payment.data.remote.api

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface StripeApi {
    @POST("v1/payment_intents")
    suspend fun createPaymentIntent(
        @Header("Authorization") auth: String,
        @Body request: Map<String, String> // Simplified for demo, normally use form-urlencoded
    ): PaymentIntentResponse

    @POST("v1/payment_methods")
    suspend fun createPaymentMethod(
        @Header("Authorization") auth: String,
        @Body request: Map<String, String>
    ): PaymentMethodResponse
}

@Serializable
data class PaymentIntentResponse(
    val id: String,
    val client_secret: String,
    val status: String
)

@Serializable
data class PaymentMethodResponse(
    val id: String,
    val type: String,
    val card: CardDetails? = null
)

@Serializable
data class CardDetails(
    val last4: String,
    val brand: String,
    val exp_month: Int,
    val exp_year: Int
)
