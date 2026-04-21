package com.train.trainingdemo.navigation.mlkit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.train.trainingdemo.navigation.AppRoute
import com.train.trainingdemo.presentation.contract.MLKitContract
import com.train.trainingdemo.presentation.view_model.MLKitViewModel

@Composable
fun MLKitNavHandler(
    navController: NavHostController,
    viewModel: MLKitViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                MLKitContract.Effect.NavigateToTranslator -> {
                    navController.navigate(AppRoute.Translate)
                }
            }
        }
    }
}
