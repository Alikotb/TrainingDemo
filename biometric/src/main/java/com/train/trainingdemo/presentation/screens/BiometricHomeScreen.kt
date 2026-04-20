package com.train.trainingdemo.presentation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.train.trainingdemo.presentation.contract.BiometricContract
import com.train.trainingdemo.presentation.view_model.BiometricViewModel
import com.train.trainingdemo.ui.component.AppBtn

@Composable
fun BiometricHomeScreen(modifier: Modifier = Modifier, viewModel: BiometricViewModel) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Spacer(Modifier.height(100.dp))
            AppBtn(
                text = "go to Pattern Lock",
                onClick = {
                    viewModel.onIntent(BiometricContract.Intent.NavigatePatternLock)
                }
            )
        }

        item {
            Spacer(Modifier.height(32.dp))
            AppBtn(
                text = "go to Finger point",
                onClick = {
                    viewModel.onIntent(BiometricContract.Intent.NavigateFingerPoint)
                }
            )
        }
    }


}