package com.train.trainingdemo.navigation.spalsh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.train.trainingdemo.navigation.AppRoute
import com.train.trainingdemo.presentation.contract.SplashContract
import com.train.trainingdemo.presentation.view_model.SplashViewModel

@Composable
fun SplashNavHandler(
    navController: NavHostController,
    viewModel: SplashViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashContract.Effect.NavigateToHome -> {
                    navController.popBackStack()
                    navController.navigate(AppRoute.HomeRoute)
                }

                SplashContract.Effect.NavigateToOnBoarding -> {
//                    navController.popBackStack()
//                    navController.navigate(AppRoute.OnBoardingRoute)
                }
            }
        }
    }
}