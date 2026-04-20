package com.train.trainingdemo.payment.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.train.trainingdemo.payment.util.CardValidator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    onBackClick: () -> Unit,
    onCardAdded: (String, String, String) -> Unit
) {
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    val isCardValid = CardValidator.isValidLuhn(cardNumber)
    val isExpiryValid = CardValidator.isValidExpiry(expiryDate)
    val isCvvValid = CardValidator.isValidCvv(cvv)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("إضافة بطاقة") },
                actions = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = cardNumber,
                onValueChange = { if (it.length <= 16) cardNumber = it },
                label = { Text("رقم البطاقة") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = cardNumber.isNotEmpty() && !isCardValid
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { if (it.length <= 5) expiryDate = it },
                    label = { Text("MM/YY") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = expiryDate.isNotEmpty() && !isExpiryValid
                )
                OutlinedTextField(
                    value = cvv,
                    onValueChange = { if (it.length <= 4) cvv = it },
                    label = { Text("CVV") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = cvv.isNotEmpty() && !isCvvValid
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onCardAdded(cardNumber, expiryDate, cvv) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = isCardValid && isExpiryValid && isCvvValid,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5E00))
            ) {
                Text("إضافة بطاقة")
            }
        }
    }
}
