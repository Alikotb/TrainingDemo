package com.train.trainingdemo.payment.domain.usecase

import com.train.trainingdemo.payment.domain.model.PaymentMethod
import com.train.trainingdemo.payment.domain.repository.PaymentRepository
import javax.inject.Inject

class ProcessPaymentUseCase @Inject constructor(
    private val repository: PaymentRepository
) {
    /**
     * Coordinate the payment process.
     * For Card payments, it returns a Client Secret to be confirmed by the Stripe SDK in the UI.
     * For Cash, it directly processes the order.
     */
    suspend operator fun invoke(paymentMethod: PaymentMethod, amount: Double): Result<String?> {
        return try {
            val amountInCents = (amount * 100).toLong()
            
            when (paymentMethod) {
                is PaymentMethod.Card -> {
                    // 1. Fetch Client Secret from backend (simulated via repository)
                    repository.createPaymentIntent(amountInCents)
                }
                is PaymentMethod.Cash -> {
                    // 2. Directly process for cash
                    repository.processPayment(paymentMethod, amount)
                    Result.success(null)
                }
                else -> Result.failure(Exception("Unsupported payment method"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
