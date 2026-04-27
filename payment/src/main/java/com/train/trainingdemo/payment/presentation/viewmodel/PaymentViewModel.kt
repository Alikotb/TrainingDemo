package com.train.trainingdemo.payment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.train.trainingdemo.payment.domain.model.PaymentMethod
import com.train.trainingdemo.payment.domain.usecase.GetOrderSummaryUseCase
import com.train.trainingdemo.payment.domain.usecase.GetPaymentMethodsUseCase
import com.train.trainingdemo.payment.domain.usecase.ProcessPaymentUseCase
import com.train.trainingdemo.payment.domain.usecase.ValidatePromoCodeUseCase
import com.train.trainingdemo.payment.presentation.contract.PaymentEffect
import com.train.trainingdemo.payment.presentation.contract.PaymentIntent
import com.train.trainingdemo.payment.presentation.contract.PaymentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getPaymentMethodsUseCase: GetPaymentMethodsUseCase,
    private val getOrderSummaryUseCase: GetOrderSummaryUseCase,
    private val processPaymentUseCase: ProcessPaymentUseCase,
    private val validatePromoCodeUseCase: ValidatePromoCodeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PaymentState())
    val state: StateFlow<PaymentState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PaymentEffect>()
    val effect: SharedFlow<PaymentEffect> = _effect.asSharedFlow()

    init {
        handleIntent(PaymentIntent.LoadData)
    }

    fun handleIntent(intent: PaymentIntent) {
        when (intent) {
            is PaymentIntent.LoadData -> loadData()
            is PaymentIntent.SelectPaymentMethod -> {
                _state.update { it.copy(selectedPaymentMethod = intent.method) }
                if (intent.method is PaymentMethod.AddNewCard) {
                    viewModelScope.launch { _effect.emit(PaymentEffect.NavigateToAddCard) }
                }
            }
            is PaymentIntent.EnterPromoCode -> _state.update { it.copy(promoCode = intent.code) }
            is PaymentIntent.ApplyPromoCode -> applyPromoCode()
            is PaymentIntent.RemovePromoCode -> removePromoCode()
            is PaymentIntent.ProcessPayment -> processPayment()
            is PaymentIntent.StripePaymentSuccess -> {
                _state.update { it.copy(isLoading = false) }
                viewModelScope.launch { _effect.emit(PaymentEffect.NavigateToSuccess) }
            }
            is PaymentIntent.StripePaymentError -> {
                _state.update { it.copy(isLoading = false) }
                viewModelScope.launch { _effect.emit(PaymentEffect.ShowError(intent.message)) }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            combine(
                getPaymentMethodsUseCase(),
                getOrderSummaryUseCase(_state.value.promoCode.takeIf { _state.value.isPromoApplied })
            ) { methods, summary ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        paymentMethods = methods,
                        orderSummary = summary,
                        selectedPaymentMethod = it.selectedPaymentMethod ?: methods.firstOrNull { m -> m !is PaymentMethod.AddNewCard }
                    )
                }
            }.catch { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }.collect()
        }
    }

    private fun applyPromoCode() {
        val code = _state.value.promoCode
        if (code.isBlank()) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            validatePromoCodeUseCase(code).onSuccess {
                _state.update { it.copy(isPromoApplied = true, isLoading = false) }
                loadData()
            }.onFailure {
                _state.update { it.copy(isLoading = false) }
                _effect.emit(PaymentEffect.ShowError(it.message ?: "Invalid code"))
            }
        }
    }

    private fun removePromoCode() {
        _state.update { it.copy(isPromoApplied = false, promoCode = "") }
        loadData()
    }

    private fun processPayment() {
        val selectedMethod = _state.value.selectedPaymentMethod ?: return
        val total = _state.value.orderSummary?.total ?: 0.0

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            processPaymentUseCase(selectedMethod, total).onSuccess { clientSecret ->
                if (clientSecret != null) {
                    // It's a card payment, trigger Stripe SDK confirmation in the UI
                    _effect.emit(PaymentEffect.ConfirmStripePayment(clientSecret))
                } else {
                    // It's cash or already processed
                    _state.update { it.copy(isLoading = false) }
                    _effect.emit(PaymentEffect.NavigateToSuccess)
                }
            }.onFailure {
                _state.update { it.copy(isLoading = false) }
                _effect.emit(PaymentEffect.ShowError(it.message ?: "Payment Failed"))
            }
        }
    }
}
