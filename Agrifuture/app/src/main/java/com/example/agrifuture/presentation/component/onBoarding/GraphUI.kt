package com.example.agrifuture.presentation.component.onBoarding

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrifuture.R
import com.example.agrifuture.presentation.model.Onboarding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OnboardingGraphUI(onboarding: Onboarding) {
    //animation
    val imageAlpha = remember { Animatable(0f) }
    val imageScale = remember { Animatable(0.8f) }
    val descriptionAlpha = remember { Animatable(0f) }

    LaunchedEffect (onboarding) {
        imageAlpha.snapTo(0f)
        imageScale.snapTo(0.8f)
        descriptionAlpha.snapTo(0f)

        launch {
            imageAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(500, easing = EaseInOutCubic)
            )
        }

        launch {
            imageScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }

        launch {
            delay(500)
            descriptionAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(500, easing =  EaseOutQuart)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment =Alignment.CenterHorizontally,
            verticalArrangement =Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = onboarding.image),
                contentDescription =null,
                modifier = Modifier
                    .size(300.dp)
                    .alpha(imageAlpha.value)
                    .scale(imageScale.value)
                    .padding(bottom = 32.dp)
            )

            Text(
                text = onboarding.description,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(descriptionAlpha.value)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GraphUIPreview1() {
    OnboardingGraphUI(Onboarding.FirstPage)
}

@Preview(showBackground = true)
@Composable
fun GraphUIPreview2() {
    OnboardingGraphUI(Onboarding.SecondPage)
}

@Preview(showBackground = true)
@Composable
fun GraphUIPreview3() {
    OnboardingGraphUI(Onboarding.ThirdPage)
}