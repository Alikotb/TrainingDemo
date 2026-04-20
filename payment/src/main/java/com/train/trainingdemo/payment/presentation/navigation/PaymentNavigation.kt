package com.train.trainingdemo.payment.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.train.trainingdemo.payment.presentation.contract.PaymentEffect
import com.train.trainingdemo.payment.presentation.screens.AddCardScreen
import com.train.trainingdemo.payment.presentation.screens.PaymentScreen
import com.train.trainingdemo.payment.presentation.screens.PaymentSuccessScreen
import com.train.trainingdemo.payment.presentation.viewmodel.PaymentViewModel
import kotlinx.coroutines.flow.collectLatest

const val PAYMENT_GRAPH_ROUTE = "payment_graph"
const val PAYMENT_SCREEN_ROUTE = "payment_main"
const val ADD_CARD_SCREEN_ROUTE = "add_card"
const val SUCCESS_SCREEN_ROUTE = "payment_success"

fun NavGraphBuilder.paymentGraph(navController: NavController) {
    navigation(startDestination = PAYMENT_SCREEN_ROUTE, route = PAYMENT_GRAPH_ROUTE) {
        composable(PAYMENT_SCREEN_ROUTE) {
            val viewModel: PaymentViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            
            androidx.compose.runtime.LaunchedEffect(Unit) {
                viewModel.effect.collectLatest { effect ->
                    when (effect) {
                        is PaymentEffect.NavigateToSuccess -> navController.navigate(SUCCESS_SCREEN_ROUTE)
                        is PaymentEffect.NavigateToAddCard -> navController.navigate(ADD_CARD_SCREEN_ROUTE)
                        is PaymentEffect.ShowError -> { /* Show snackbar */ }
                        else -> {}
                    }
                }
            }

            PaymentScreen(
                state = state,
                onIntent = viewModel::handleIntent,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(ADD_CARD_SCREEN_ROUTE) {
            AddCardScreen(
                onBackClick = { navController.popBackStack() },
                onCardAdded = { _, _, _ -> 
                    // In a real app, save card then pop
                    navController.popBackStack()
                }
            )
        }

        composable(SUCCESS_SCREEN_ROUTE) {
            PaymentSuccessScreen(
                onDoneClick = {
                    navController.popBackStack(PAYMENT_SCREEN_ROUTE, inclusive = true)
                    // Navigate to home or orders
                }
            )
        }
    }
}
