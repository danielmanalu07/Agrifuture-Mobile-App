package com.example.agrifuture.presentation.component.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.model.Orders
import com.example.agrifuture.presentation.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OrderSection(orderList: List<Orders>, navController: NavController,onRefresh: () -> Unit) {
    var selectedPaymentMethod by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    orderList.firstOrNull()?.let { order ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                // Customer Information
                Text(
                    text = "Customer",
                    fontSize = 15.sp,
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = "Customer Icon",
                        tint = colorResource(id = R.color.black),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = order.customer?.name ?: "Unknown",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_phone),
                        contentDescription = "Phone Icon",
                        tint = colorResource(id = R.color.black),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = order.customer?.phone ?: "No phone available",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                PaymentInfoCard(totalPrice = order.total_price)

                Spacer(modifier = Modifier.height(40.dp))

                // Payment Method
                PaymentMethodSelection(
                    selectedMethod = selectedPaymentMethod,
                    onMethodSelected = { selectedPaymentMethod = it }
                )

                Spacer(modifier = Modifier.weight(1f))

                // Pay Button
                PaymentButton(
                    selectedPaymentMethod = selectedPaymentMethod,
                    onPayClick = {
                        if (selectedPaymentMethod.isNotEmpty()) {
                            showDialog = true
                        } else {
                            showError = true
                        }
                    }
                )

                if (showError) {
                    Text(
                        text = "Please select a payment method",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // Dialog and Success Animation
            PaymentDialog(
                showDialog = showDialog,
                onDismiss = { showDialog = false },
                onConfirm = {
                    showDialog = false
                    showSuccess = true
                    scope.launch {
                        delay(2000)
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Order.route) { inclusive = true }
                        }
                    }
                }
            )

            if (showSuccess) {
                SuccessAnimation()
            }
        }
    }
}


@Composable
fun PaymentInfoCard(totalPrice: Float) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = colorResource(id = R.color.background))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Total Payment",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = colorResource(id = R.color.black)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Rp. $totalPrice",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.black)
            )
        }
    }
}

@Composable
fun PaymentMethodSelection(selectedMethod: String, onMethodSelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color = colorResource(id = R.color.background))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Payment Method",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.size(8.dp))
            listOf("Cash", "Transfer Bank", "E-Wallet").forEach { method ->
                PaymentMethodItem(method, selectedMethod, onMethodSelected)
            }
        }
    }
}

@Composable
fun PaymentButton(selectedPaymentMethod: String, onPayClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onPayClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.green),
            disabledContainerColor = colorResource(id = R.color.gray)
        ),
        enabled = selectedPaymentMethod.isNotEmpty()
    ) {
        Text("Pay", color = colorResource(id = R.color.white))
    }
}

@Composable
fun PaymentDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(id = R.color.background))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Virtual Account Payment",
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.black)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "BCA - 1234567890",
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.black)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onConfirm) {
                    Text("Confirm", color = colorResource(id = R.color.white))
                }
            }
        }
    }
}

@Composable
fun SuccessAnimation() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_success1),
                contentDescription = "Success Icon",
                tint = colorResource(id = R.color.green),
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Payment Successful!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.green)
            )
        }
    }
}


@Composable
fun PaymentMethodItem(method: String, selectedMethod: String, onSelect: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp).background(colorResource(id = R.color.white)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = (method == selectedMethod),
            onCheckedChange = { onSelect(method) },
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = method,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.black)
        )
    }
}