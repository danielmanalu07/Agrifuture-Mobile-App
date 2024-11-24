package com.example.agrifuture.presentation.component.notification

import android.icu.util.TimeUnit
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrifuture.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.agrifuture.presentation.model.Notification as Notification

@Composable
fun NotificationItem(notification: Notification) {
    val context = LocalContext.current
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconRes = when (notification.type) {
            "payment" -> R.drawable.ic_payment
            "order" -> R.drawable.ic_order
            else -> null
        }

        iconRes?.let { painterResource(id = it) }?.let {
            Image(
                painter = it,
                contentDescription = "null",
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column (
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = notification.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = colorResource(id = R.color.black)
            )

            Text(
                text = notification.text,
                fontSize = 14.sp,
                color = colorResource(id = R.color.gray),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column (
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${calculateTime(notification.createdAt)} ago",
                fontSize = 14.sp,
                color = colorResource(id = R.color.gray)
            )

            Spacer(modifier = Modifier.height(20.dp))

            ClickableText(
                text = AnnotatedString("Mark As Read"),
                style = TextStyle(color = colorResource(id = R.color.green), fontSize = 14.sp),
                onClick = {
                    Toast.makeText(context, "Mark As Read", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

fun calculateTime(createdAt: String): String{
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val createdDate = dateFormat.parse(createdAt)
    val currentDate = Date()

    val diffInMillis = currentDate.time - (createdDate?.time?: currentDate.time)
    val hours = java.util.concurrent.TimeUnit.MILLISECONDS.toHours(diffInMillis)
    val minutes = java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60

    return  if (hours > 0){
        "$hours h"
    } else {
        "$minutes m"
    }
}
