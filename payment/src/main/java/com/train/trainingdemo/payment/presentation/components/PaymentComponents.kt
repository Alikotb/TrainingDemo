package com.train.trainingdemo.payment.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.train.trainingdemo.payment.domain.model.PaymentMethod

@Composable
fun PaymentMethodItem(
    method: PaymentMethod,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            when (method) {
                is PaymentMethod.Card -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "xxxx-${method.lastFourDigits}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        // In a real app, use real icons
                        Box(
                            modifier = Modifier
                                .size(32.dp, 20.dp)
                                .background(Color.LightGray, RoundedCornerShape(4.dp))
                        )
                    }
                    Text(
                        text = method.expiryDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                is PaymentMethod.AddNewCard -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "أضف بطاقة جديدة",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
                is PaymentMethod.Cash -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "نقداً",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        // Cash icon placeholder
                        Icon(Icons.Default.Info, contentDescription = null)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        RadioButton(
            selected = isSelected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
        )
    }
}

@Composable
fun SummaryRow(
    label: String,
    value: String,
    isTotal: Boolean = false,
    isDiscount: Boolean = false,
    hasInfo: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            style = if (isTotal) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyLarge,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal,
            color = if (isDiscount) Color.Red else Color.Unspecified,
            modifier = if (isDiscount) Modifier.background(Color(0xFFFFFF00)).padding(horizontal = 4.dp) else Modifier
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (hasInfo) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = label,
                style = if (isTotal) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyLarge,
                fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}
