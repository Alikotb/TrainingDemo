package com.train.trainingdemo.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.train.trainingdemo.navigation.biometric.BiometricNavHandler
import com.train.trainingdemo.navigation.home.HomeNavHandler
import com.train.trainingdemo.navigation.spalsh.SplashNavHandler
import com.train.trainingdemo.payment.presentation.contract.PaymentEffect
import com.train.trainingdemo.payment.presentation.screens.PaymentScreen
import com.train.trainingdemo.payment.presentation.viewmodel.PaymentViewModel
import com.train.trainingdemo.presentation.screens.BiometricHomeScreen
import com.train.trainingdemo.presentation.screens.HomeScreen
import com.train.trainingdemo.presentation.screens.MapScreen
import com.train.trainingdemo.presentation.screens.SplashScreen
import com.train.trainingdemo.presentation.view_model.BiometricViewModel
import com.train.trainingdemo.presentation.view_model.HomeViewModel
import com.train.trainingdemo.presentation.view_model.MapViewModel
import com.train.trainingdemo.presentation.view_model.SplashViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    val context = LocalContext.current
    
    NavHost(
        navController = navController,
        startDestination = AppRoute.SplashRoute
    ) {
        composable<AppRoute.SplashRoute> {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashNavHandler(navController = navController, viewModel = viewModel)
            SplashScreen(modifier.padding(innerPadding), viewModel)
        }
        composable<AppRoute.HomeRoute> {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeNavHandler(navController = navController, viewModel = viewModel)
            HomeScreen(modifier.padding(innerPadding), viewModel = viewModel)
        }
        composable<AppRoute.MapRoute> {
            val viewModel = hiltViewModel<MapViewModel>()
            MapScreen(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(), viewModel
            )
        }
        composable<AppRoute.BiometricHome> {
            val viewModel = hiltViewModel<BiometricViewModel>()
            BiometricNavHandler(navController = navController, viewModel = viewModel)
            BiometricHomeScreen(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(), viewModel
            )
        }
        composable<AppRoute.Payment> {
            val viewModel = hiltViewModel<PaymentViewModel>()
            val state by viewModel.state.collectAsState()
            
            // --- REAL STRIPE INTEGRATION ---
            // In a real production app, the Stripe key comes from your backend.
            val stripe = remember { Stripe(context, "pk_test_51BTj7S2eX4qn7p00nU8N7a7p") }
            
            LaunchedEffect(Unit) {
                viewModel.effect.collectLatest { effect ->
                    when (effect) {
                        is PaymentEffect.ConfirmStripePayment -> {
                            val params = ConfirmPaymentIntentParams.create(effect.clientSecret)
                            // confirmPayment triggers the 3DS challenge if needed
                            // stripeAccountId is used for Connected Accounts (e.g. marketplaces)
                            // Omit it if you're charging directly to your account.
                            stripe.confirmPayment(
                                context as FragmentActivity,
                                params,
                                stripeAccountId = effect.stripeAccountId // null by default for direct charges
                            )
                        }
                        is PaymentEffect.NavigateToSuccess -> {
                            navController.navigate(AppRoute.SplashRoute) { // Or Success screen
                                popUpTo(AppRoute.HomeRoute) { inclusive = false }
                            }
                        }
                        is PaymentEffect.ShowError -> {
                            Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                        }
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
    }
}
