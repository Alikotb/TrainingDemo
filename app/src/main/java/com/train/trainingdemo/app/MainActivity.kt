package com.train.trainingdemo.app


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.train.trainingdemo.navigation.AppNavHost
import com.train.trainingdemo.ui.theme.TrainingDemoTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Configuration.getInstance().userAgentValue = packageName

        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            TrainingDemoTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(
                        innerPadding = innerPadding,
                        navController = navController,
                    )
                }
            }
        }
    }
}




