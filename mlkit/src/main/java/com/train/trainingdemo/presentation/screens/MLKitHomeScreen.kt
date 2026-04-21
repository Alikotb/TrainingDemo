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
import com.train.trainingdemo.ui.component.AppBtn
import com.train.trainingdemo.presentation.contract.MLKitContract
import com.train.trainingdemo.presentation.view_model.MLKitViewModel

@Composable
fun MLKitHomeScreen(modifier: Modifier = Modifier, viewModel: MLKitViewModel) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(Modifier.height(64.dp))

            Text(
                "MLKit",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 30.sp,

                    )
            )
        }
        item {
            Spacer(Modifier.height(16.dp))
            AppBtn(
                text = "go to Translator",
                onClick = {
                    viewModel.onIntent(MLKitContract.Intent.NavigateToTranslator)
                }
            )
        }

    }


}