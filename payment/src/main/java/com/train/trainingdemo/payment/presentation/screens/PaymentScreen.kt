package com.train.trainingdemo.payment.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.train.trainingdemo.payment.presentation.contract.PaymentIntent
import com.train.trainingdemo.payment.presentation.contract.PaymentState
import com.train.trainingdemo.payment.presentation.components.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    state: PaymentState,
    onIntent: (PaymentIntent) -> Unit,
    onBackClick: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "تنفيذ الطلب",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "طلبات مارت",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { onIntent(PaymentIntent.ProcessPayment) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp).padding(bottom = 32.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5E00)),
                shape = RoundedCornerShape(28.dp),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("تنفيذ الطلب", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                "الدفع من خلال",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column {
                    state.paymentMethods.forEach { method ->
                        PaymentMethodItem(
                            method = method,
                            isSelected = state.selectedPaymentMethod == method,
                            onSelect = { onIntent(PaymentIntent.SelectPaymentMethod(method)) }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                    }
                    
                    PaymentMethodItem(
                        method = com.train.trainingdemo.payment.domain.model.PaymentMethod.AddNewCard,
                        isSelected = false,
                        onSelect = { onIntent(PaymentIntent.SelectPaymentMethod(com.train.trainingdemo.payment.domain.model.PaymentMethod.AddNewCard)) }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF5F9FF))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "(PCI) مؤمن وفق معايير حماية بيانات الدفع",
                            color = Color(0xFF1976D2),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFF1976D2),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "أضف قسيمة",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { /* Focus promo text field */ }
                )
                Text(
                    "وفّر على طلبك",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    if (state.isPromoApplied) {
                        IconButton(onClick = { onIntent(PaymentIntent.RemovePromoCode) }) {
                            Icon(Icons.Default.Close, contentDescription = "Remove")
                        }
                    } else {
                        Text(
                            "أضف",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable { onIntent(PaymentIntent.ApplyPromoCode) }
                        )
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    BasicTextField(
                        value = state.promoCode,
                        onValueChange = { onIntent(PaymentIntent.EnterPromoCode(it)) },
                        modifier = Modifier.width(150.dp),
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                        decorationBox = { innerTextField ->
                            if (state.promoCode.isEmpty()) {
                                Text("خصم 20% مع ماستركارد", color = Color.Gray, textAlign = TextAlign.End)
                            }
                            innerTextField()
                        },
                        enabled = !state.isPromoApplied
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "ملخص الدفع",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            state.orderSummary?.let { summary ->
                SummaryRow("المجموع الفرعي", "${"%.2f".format(Locale.ENGLISH, summary.subtotal)} ج.م")
                if (summary.discount > 0) {
                    SummaryRow("الخصم", "- ${"%.2f".format(Locale.ENGLISH, summary.discount)} ج.م", isDiscount = true)
                }
                SummaryRow("رسوم التوصيل", "${"%.2f".format(Locale.ENGLISH, summary.deliveryFee)} ج.م", hasInfo = true)
                SummaryRow("رسوم الخدمة", "${"%.2f".format(Locale.ENGLISH, summary.serviceFee)} ج.m", hasInfo = true)
                
                Spacer(modifier = Modifier.height(8.dp))
                SummaryRow("المبلغ الإجمالي", "${"%.2f".format(Locale.ENGLISH, summary.total)} ج.م", isTotal = true)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "عند تنفيذ هذا الطلب عبر بطاقة الائتمان انت توافق على الشروط و الأحكام",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: androidx.compose.ui.text.TextStyle = LocalTextStyle.current,
    decorationBox: @Composable (@Composable () -> Unit) -> Unit = { it() },
    enabled: Boolean = true
) {
    androidx.compose.foundation.text.BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = textStyle,
        decorationBox = decorationBox,
        enabled = enabled
    )
}
