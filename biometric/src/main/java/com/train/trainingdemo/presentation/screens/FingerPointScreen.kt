package com.train.trainingdemo.presentation.screens

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.train.trainingdemo.presentation.view_model.BiometricViewModel
import java.util.concurrent.Executor


@Composable
fun FingerPointScreen(modifier: Modifier = Modifier, viewModel: BiometricViewModel) {
    val context = LocalContext.current
    val executor: Executor = ContextCompat.getMainExecutor(context)

    var authResult by remember { mutableStateOf("Not Authenticated") }
    val activity = context as FragmentActivity

    // Prompt Info
    val promptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Authentication is mandatory to continue")
            .setNegativeButtonText("Retry")
            .build()
    }

    // Create BiometricPrompt
    val biometricPrompt = remember {
        var prompt: BiometricPrompt? = null
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                authResult = "Authentication Succeeded ✅"
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                authResult = "Error: $errString"
                // If user cancels or clicks negative button, re-trigger authentication to force it
                if (errorCode == BiometricPrompt.ERROR_USER_CANCELED ||
                    errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON
                ) {
                    prompt?.authenticate(promptInfo)
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                authResult = "Authentication Failed ❌"
            }
        }
        prompt = BiometricPrompt(activity, executor, callback)
        prompt
    }

    // UI
    Column(
        modifier = modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(authResult, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            val biometricManager = BiometricManager.from(context)
            when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    biometricPrompt.authenticate(promptInfo)
                }

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                    authResult = "No biometric hardware found"

                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                    authResult = "Biometric hardware unavailable"

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                    authResult = "No biometric credentials enrolled"

                else -> authResult = "Biometric not supported"
            }
        }) {
            Text("Authenticate")
        }
    }
}
