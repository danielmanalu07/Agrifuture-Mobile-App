package com.example.agrifuture.presentation.component.order

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.component.authentication.ButtonUI
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.viewModel.CustomerVM
import com.example.agrifuture.presentation.viewModel.OrderVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OrderSection(orderVM: OrderVM, navController: NavController) {
    val customerVM = CustomerVM()
    val customer = customerVM.getCustomers()
    val order = orderVM.getOrders()

    var selectedPaymentMethod by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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
            Text(
                text = "Customer",
                fontSize = 15.sp,
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Customer Icon",
                    tint = colorResource(id = R.color.black),
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                customer.firstOrNull()?.name?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone),
                    contentDescription = "Phone Icon",
                    tint = colorResource(id = R.color.black),
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                customer.firstOrNull()?.phone?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            Box(
                modifier = Modifier
                    .width(300.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = colorResource(id = R.color.background))
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Total Payment",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.black)
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        text = "Rp. ${order[0].total_price}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.black)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = colorResource(id = R.color.background))
                    .padding(16.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "Payment Method",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    PaymentMethodItem("Cash", selectedPaymentMethod) { selectedPaymentMethod = it }
                    PaymentMethodItem("Transfer Bank", selectedPaymentMethod) { selectedPaymentMethod = it }
                    PaymentMethodItem("E-Wallet", selectedPaymentMethod) { selectedPaymentMethod = it }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (selectedPaymentMethod.isNotEmpty()) {
                        showDialog = true
                    } else {
                        showError = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.green),
                    disabledContainerColor = colorResource(id = R.color.gray)
                ),
                enabled = selectedPaymentMethod.isNotEmpty()
            ) {
                Text("Pay", color = colorResource(id = R.color.white))
            }

            if (showError) {
                Text(
                    text = "Please select a payment method",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = showDialog,
            enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(500)),
            exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(500)),
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(id = R.color.background))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
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
                    Button(
                        onClick = {
                            showDialog = false
                            showSuccess = true
                            scope.launch {
                                delay(2000)
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Order.route) { inclusive = true }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.green)
                        )
                    ) {
                        Text("Confirm", color = colorResource(id = R.color.white))
                    }
                }
            }
        }

        if (showSuccess) {
            val scaleAnim = remember { Animatable(0f) }
            LaunchedEffect(Unit) {
                scaleAnim.animateTo(1f, animationSpec = tween(1000))
            }

            Box(
                modifier = Modifier
                    .background(colorResource(id = R.color.background))
                    .padding(16.dp)
                    .align(Alignment.Center)
                    .scale(scaleAnim.value),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(colorResource(id = R.color.green))
                            .scale(scaleAnim.value),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_success1),
                            contentDescription = "Success Icon",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp).scale(scaleAnim.value)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Payment Successful!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.green),
                        modifier = Modifier.scale(scaleAnim.value)
                    )
                }
            }
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
@Preview(showBackground = true)
@Composable
private fun PreviewOrderSection() {
    val orderVM = OrderVM()
    val navController = rememberNavController()
    OrderSection(orderVM = orderVM, navController = navController)
}
