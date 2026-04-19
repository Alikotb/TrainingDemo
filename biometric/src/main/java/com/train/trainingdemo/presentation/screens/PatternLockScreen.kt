package com.train.trainingdemo.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.train.trainingdemo.presentation.component.ComposeLock
import com.train.trainingdemo.presentation.utils.model.ComposeLockCallback
import com.train.trainingdemo.presentation.utils.model.Dot
import com.train.trainingdemo.presentation.view_model.BiometricViewModel

@Composable
fun PatternLockScreen(modifier: Modifier = Modifier, viewModel: BiometricViewModel) {
    val context = LocalContext.current
    var firstPattern by remember { mutableStateOf<List<Int>?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var instruction by remember { mutableStateOf("Draw your pattern") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Success") },
            text = { Text(text = "Pattern matched and saved successfully!") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    firstPattern = null
                    instruction = "Draw your pattern"
                }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.7f))
        Text(
            text = instruction,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
        )

        Spacer(modifier = Modifier.weight(0.3f))

        ComposeLock(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .aspectRatio(1f)/*.background(Color.Green)*/, // Ensure it's a square
            paddingAround = 100f,
            dimension = 3,
            sensitivity = 80f,
            dotsColor = Color.LightGray,
            dotsSize = 12f,
            linesColor = Color.Cyan,
            linesStroke = 15f,
            animationDuration = 200,
            animationDelay = 100,
            callback = object : ComposeLockCallback {
                override fun onStart(dot: Dot) {}

                override fun onDotConnected(dot: Dot) {}

                override fun onResult(result: List<Dot>) {
                    val currentPattern = result.map { it.id }

                    if (currentPattern.size < 4) {
                        Toast.makeText(context, "Connect at least 4 dots", Toast.LENGTH_SHORT).show()
                        return
                    }

                    if (firstPattern == null) {
                        firstPattern = currentPattern
                        instruction = "Draw the pattern again to confirm"
                        Toast.makeText(context, "Pattern recorded", Toast.LENGTH_SHORT).show()
                    } else {
                        if (firstPattern == currentPattern) {
                            showDialog = true
                            instruction = "Pattern Confirmed"
                        } else {
                            firstPattern = null
                            instruction = "Patterns didn't match. Try again"
                            Toast.makeText(context, "Mismatch! Start over", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}
