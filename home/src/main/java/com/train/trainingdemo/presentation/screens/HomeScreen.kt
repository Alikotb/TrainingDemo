package com.train.trainingdemo.presentation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.train.trainingdemo.presentation.component.HomeBtn
import com.train.trainingdemo.presentation.contract.HomeContract
import com.train.trainingdemo.presentation.view_model.HomeViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(Modifier.height(64.dp))

            Text(
                "HomeScreen",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 30.sp,

                    )
            )
        }
        item {
            Spacer(Modifier.height(16.dp))
            HomeBtn(
                text = "go to map",
                onClick = {
                    viewModel.onIntent(HomeContract.Intent.NavigateToMap)
                }
            )
        }
    }


}