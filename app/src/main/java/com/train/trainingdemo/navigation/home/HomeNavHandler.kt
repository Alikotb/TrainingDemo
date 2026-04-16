package com.train.trainingdemo.navigation.home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.train.trainingdemo.navigation.AppRoute
import com.train.trainingdemo.presentation.contract.HomeContract
import com.train.trainingdemo.presentation.view_model.HomeViewModel

@Composable
fun HomeNavHandler(
    navController: NavHostController,
    viewModel: HomeViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                HomeContract.Effect.NavigateToMap  -> {
                    navController.navigate(AppRoute.MapRoute)
                }

            }
//                HomeContract.Effect.NavigateToHome -> {
//                    navController.popBackStack()
//                    navController.navigate(AppRoute.HomeRoute)
//                }
//
//                HomeContract.Effect.NavigateToOnBoarding -> {
////                    navController.popBackStack()
////                    navController.navigate(AppRoute.OnBoardingRoute)
//                }
//            }
        }
    }
}