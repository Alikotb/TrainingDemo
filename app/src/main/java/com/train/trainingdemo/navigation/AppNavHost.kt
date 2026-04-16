package com.train.trainingdemo.navigation
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.train.trainingdemo.navigation.home.HomeNavHandler
import com.train.trainingdemo.navigation.spalsh.SplashNavHandler
import com.train.trainingdemo.presentation.screens.HomeScreen
import com.train.trainingdemo.presentation.screens.MapScreen
import com.train.trainingdemo.presentation.screens.SplashScreen
import com.train.trainingdemo.presentation.view_model.HomeViewModel
import com.train.trainingdemo.presentation.view_model.MapViewModel
import com.train.trainingdemo.presentation.view_model.SplashViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues,
//    mainViewModel: MainViewModel
) {
//    val isOnline by mainViewModel.isOnline.observeAsState(initial = true)
    NavHost(
        navController = navController,
        startDestination = AppRoute.SplashRoute
    ) {
        composable<AppRoute.SplashRoute> {

            val viewModel = hiltViewModel<SplashViewModel>()
            SplashNavHandler(navController = navController, viewModel = viewModel)
            SplashScreen(modifier.padding(innerPadding),viewModel)
        }
        composable<AppRoute.HomeRoute> {

            val viewModel = hiltViewModel<HomeViewModel>()
            HomeNavHandler(navController = navController, viewModel = viewModel)
            HomeScreen(modifier.padding(innerPadding),viewModel)
        }
        composable<AppRoute.MapRoute> {

            val viewModel = hiltViewModel<MapViewModel>()
            MapScreen(modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()  ,viewModel)
        }


    }
}