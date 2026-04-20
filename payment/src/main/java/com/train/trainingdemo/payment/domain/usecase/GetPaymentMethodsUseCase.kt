package com.train.trainingdemo.payment.domain.usecase

import com.train.trainingdemo.payment.domain.model.PaymentMethod
import com.train.trainingdemo.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaymentMethodsUseCase @Inject constructor(
    private val repository: PaymentRepository
) {
    operator fun invoke(): Flow<List<PaymentMethod>> = repository.getPaymentMethods()
}
