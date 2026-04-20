package com.train.trainingdemo.navigation.biometric
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.train.trainingdemo.navigation.AppRoute
import com.train.trainingdemo.presentation.contract.BiometricContract
import com.train.trainingdemo.presentation.view_model.BiometricViewModel

@Composable
fun BiometricNavHandler(
    navController: NavHostController,
    viewModel: BiometricViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                BiometricContract.Effect.NavigatePatternLock  -> {
                    navController.navigate(AppRoute.PatternLock)
                }
                BiometricContract.Effect.NavigateFingerPoint  -> {
                    navController.navigate(AppRoute.FingerPoint)
                }

            }
        }
    }
}