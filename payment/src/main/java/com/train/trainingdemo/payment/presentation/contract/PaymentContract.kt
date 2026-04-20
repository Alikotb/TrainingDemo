package com.train.trainingdemo.payment.presentation.contract

import com.train.trainingdemo.payment.domain.model.OrderSummary
import com.train.trainingdemo.payment.domain.model.PaymentMethod

data class PaymentState(
    val isLoading: Boolean = false,
    val paymentMethods: List<PaymentMethod> = emptyList(),
    val selectedPaymentMethod: PaymentMethod? = null,
    val orderSummary: OrderSummary? = null,
    val promoCode: String = "",
    val isPromoApplied: Boolean = false,
    val error: String? = null
)

sealed interface PaymentIntent {
    data object LoadData : PaymentIntent
    data class SelectPaymentMethod(val method: PaymentMethod) : PaymentIntent
    data class EnterPromoCode(val code: String) : PaymentIntent
    data object ApplyPromoCode : PaymentIntent
    data object RemovePromoCode : PaymentIntent
    data object ProcessPayment : PaymentIntent
    data object StripePaymentSuccess : PaymentIntent
    data class StripePaymentError(val message: String) : PaymentIntent
}

sealed interface PaymentEffect {
    data object NavigateToSuccess : PaymentEffect
    data class ShowError(val message: String) : PaymentEffect
    data object NavigateToAddCard : PaymentEffect
    data class ConfirmStripePayment(
        val clientSecret: String,
        val paymentMethodId: String? = null,
        val stripeAccountId: String? = null
    ) : PaymentEffect
}
