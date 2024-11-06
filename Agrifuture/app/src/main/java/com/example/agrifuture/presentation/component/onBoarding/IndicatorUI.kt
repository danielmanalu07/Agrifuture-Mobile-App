package com.example.agrifuture.presentation.component.onBoarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.agrifuture.R

@Composable
fun IndicatorUI(
    pageSize: Int,
    currentPage: Int,
    selectedColor: Color = colorResource(id = R.color.dark_green),
    unSelectedColor: Color = colorResource(id = R.color.gray)
) {
    Row (
    horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        repeat(pageSize){
            Spacer(modifier = Modifier.size(3.dp))

            Box(
                modifier = Modifier
                    .height(10.dp)
                    .width(width = if (it == currentPage) 15.dp else 10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = if (it == currentPage) selectedColor else unSelectedColor)
            ) {}

            Spacer(modifier = Modifier.size(3.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IndicatorPreview1() {
    IndicatorUI(pageSize = 3, currentPage = 0)
}

@Preview(showBackground = true)
@Composable
fun IndicatorPreview2() {
    IndicatorUI(pageSize = 3, currentPage = 1)
}

@Preview(showBackground = true)
@Composable
fun IndicatorPreview3() {
    IndicatorUI(pageSize = 3, currentPage = 2)
}