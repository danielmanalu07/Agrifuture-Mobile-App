package com.example.agrifuture.presentation.component.onBoarding

import android.content.res.Resources.Theme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrifuture.R

@Composable
fun ButtonUI(
    text: String,
    backgroundColor: Color = colorResource(id = R.color.dark_green),
    textColor: Color = colorResource(id = R.color.white),
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.titleMedium,
    fontSize: Int =15,
    onClick: () -> Unit
) {
   Button(
       onClick =onClick,
       colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
           contentColor =textColor
       ), shape = RoundedCornerShape(10.dp)
   ) {
       Text(
           text = text,
           fontSize = fontSize.sp,
           style = textStyle,
       )
   }
}

@Preview
@Composable
fun NextButton() {
    ButtonUI(
        text = "Next",
    ) { }
}

@Preview
@Composable
fun BackButton() {
    ButtonUI(
        text = "Back",
        backgroundColor = colorResource(id = R.color.gray),
        textColor = Color.White,
        textStyle = MaterialTheme.typography.bodySmall,
        fontSize = 13
    ) { }
}