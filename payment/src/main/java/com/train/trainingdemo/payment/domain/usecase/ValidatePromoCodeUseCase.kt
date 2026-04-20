package com.train.trainingdemo.payment.domain.usecase

import com.train.trainingdemo.payment.domain.repository.PaymentRepository
import javax.inject.Inject

class ValidatePromoCodeUseCase @Inject constructor(
    private val repository: PaymentRepository
) {
    suspend operator fun invoke(code: String): Result<Double> = repository.validatePromoCode(code)
}
