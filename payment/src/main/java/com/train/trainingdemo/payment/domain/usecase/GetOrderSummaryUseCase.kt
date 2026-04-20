package com.train.trainingdemo.payment.domain.usecase

import com.train.trainingdemo.payment.domain.model.OrderSummary
import com.train.trainingdemo.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderSummaryUseCase @Inject constructor(
    private val repository: PaymentRepository
) {
    operator fun invoke(promoCode: String? = null): Flow<OrderSummary> = repository.getOrderSummary(promoCode)
}
