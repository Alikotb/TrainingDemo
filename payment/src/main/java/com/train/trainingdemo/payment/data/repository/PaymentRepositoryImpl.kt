package com.train.trainingdemo.payment.data.repository

import com.train.trainingdemo.payment.domain.model.CardType
import com.train.trainingdemo.payment.domain.model.OrderSummary
import com.train.trainingdemo.payment.domain.model.PaymentMethod
import com.train.trainingdemo.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor() : PaymentRepository {

    private val paymentMethods = MutableStateFlow<List<PaymentMethod>>(
        listOf(
            PaymentMethod.Card("1", "3533", "11/28", CardType.MASTERCARD),
            PaymentMethod.Cash
        )
    )

    override fun getPaymentMethods(): Flow<List<PaymentMethod>> = paymentMethods

    override fun getOrderSummary(promoCode: String?): Flow<OrderSummary> = flow {
        val subtotal = 382.00
        val discount = if (promoCode?.lowercase() == "save20") 76.4 else 0.0
        val deliveryFee = 23.99
        val serviceFee = 9.90
        val total = subtotal - discount + deliveryFee + serviceFee
        
        emit(
            OrderSummary(
                subtotal = subtotal,
                discount = discount,
                deliveryFee = deliveryFee,
                serviceFee = serviceFee,
                total = total
            )
        )
    }

    override suspend fun processPayment(paymentMethod: PaymentMethod, amount: Double): Result<Unit> {
        delay(2000)
        return Result.success(Unit)
    }

    override suspend fun validatePromoCode(code: String): Result<Double> {
        delay(1000)
        return if (code.lowercase() == "save20") {
            Result.success(76.4)
        } else {
            Result.failure(Exception("كود الخصم غير صحيح"))
        }
    }

    override suspend fun createPaymentIntent(amount: Long): Result<String> {
        delay(1000)
        return Result.success("pi_test_secret_12345")
    }
    
     fun addCard(lastFour: String, expiry: String) {
        paymentMethods.update { current ->
            val newList = current.toMutableList()
            newList.add(0, PaymentMethod.Card(java.util.UUID.randomUUID().toString(), lastFour, expiry, CardType.VISA))
            newList
        }
    }
}
