package com.train.trainingdemo.payment.presentation.viewmodel

import com.google.common.truth.Truth.assertThat
import com.train.trainingdemo.payment.domain.model.OrderSummary
import com.train.trainingdemo.payment.domain.model.PaymentMethod
import com.train.trainingdemo.payment.domain.usecase.GetOrderSummaryUseCase
import com.train.trainingdemo.payment.domain.usecase.GetPaymentMethodsUseCase
import com.train.trainingdemo.payment.domain.usecase.ProcessPaymentUseCase
import com.train.trainingdemo.payment.domain.usecase.ValidatePromoCodeUseCase
import com.train.trainingdemo.payment.presentation.contract.PaymentEffect
import com.train.trainingdemo.payment.presentation.contract.PaymentIntent
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PaymentViewModelTest {

    private lateinit var viewModel: PaymentViewModel
    private val getPaymentMethodsUseCase: GetPaymentMethodsUseCase = mockk()
    private val getOrderSummaryUseCase: GetOrderSummaryUseCase = mockk()
    private val processPaymentUseCase: ProcessPaymentUseCase = mockk()
    private val validatePromoCodeUseCase: ValidatePromoCodeUseCase = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        val methods = listOf(PaymentMethod.Cash)
        val summary = OrderSummary(100.0, 10.0, 5.0, 2.0, 97.0)
        
        every { getPaymentMethodsUseCase() } returns flowOf(methods)
        every { getOrderSummaryUseCase(any()) } returns flowOf(summary)
        
        viewModel = PaymentViewModel(
            getPaymentMethodsUseCase,
            getOrderSummaryUseCase,
            processPaymentUseCase,
            validatePromoCodeUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `LoadData intent should update state with payment methods and summary`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        
        val state = viewModel.state.value
        assertThat(state.paymentMethods).isNotEmpty()
        assertThat(state.orderSummary).isNotNull()
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `ProcessPayment success should emit NavigateToSuccess effect`() = runTest {
        coEvery { processPaymentUseCase(any(), any()) } returns Result.success(null)
        
        val effects = mutableListOf<PaymentEffect>()
        val job = launch(testDispatcher) {
            viewModel.effect.collect { effects.add(it) }
        }
        
        viewModel.handleIntent(PaymentIntent.ProcessPayment)
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertThat(effects).contains(PaymentEffect.NavigateToSuccess)
        job.cancel()
    }

    @Test
    fun `SelectPaymentMethod with AddNewCard should emit NavigateToAddCard effect`() = runTest {
        val effects = mutableListOf<PaymentEffect>()
        val job = launch(testDispatcher) {
            viewModel.effect.collect { effects.add(it) }
        }
        
        viewModel.handleIntent(PaymentIntent.SelectPaymentMethod(PaymentMethod.AddNewCard))
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertThat(effects).contains(PaymentEffect.NavigateToAddCard)
        job.cancel()
    }
}
