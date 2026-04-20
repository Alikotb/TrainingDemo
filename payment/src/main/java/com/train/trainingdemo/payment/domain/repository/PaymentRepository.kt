package com.train.trainingdemo.payment.domain.repository

import com.train.trainingdemo.payment.domain.model.OrderSummary
import com.train.trainingdemo.payment.domain.model.PaymentMethod
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getPaymentMethods(): Flow<List<PaymentMethod>>
    fun getOrderSummary(promoCode: String? = null): Flow<OrderSummary>
    suspend fun processPayment(paymentMethod: PaymentMethod, amount: Double): Result<Unit>
    suspend fun validatePromoCode(code: String): Result<Double>
    suspend fun createPaymentIntent(amount: Long): Result<String>
}
