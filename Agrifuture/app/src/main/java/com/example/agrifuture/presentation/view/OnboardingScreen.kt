package com.example.agrifuture.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.component.onBoarding.ButtonUI
import com.example.agrifuture.presentation.component.onBoarding.IndicatorUI
import com.example.agrifuture.presentation.component.onBoarding.OnboardingGraphUI
import com.example.agrifuture.presentation.model.Onboarding
import com.example.agrifuture.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    navController: NavController,
    onFinished: () -> Unit
) {
    val pages = listOf(
        Onboarding.FirstPage, Onboarding.SecondPage, Onboarding.ThirdPage,
    )

    val pagerState = rememberPagerState (initialPage = 0) {
        pages.size
    }

    val buttonState = remember {
        derivedStateOf{
            when (pagerState.currentPage){
                0 -> listOf("", "Next")
                1 -> listOf("Back", "Next")
                2 -> listOf("Back", "Start")
                else -> listOf("", "")
            }
        }
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Box (
                modifier = Modifier.fillMaxWidth().background(color = colorResource(id = R.color.background)).padding(10.dp, 0.dp),
                contentAlignment = Alignment.TopEnd,

                ) {
                TextButton(
                    onClick = {
                        onFinished()
                    },
                )
                {
                    Text(text = "Skip", color = colorResource(id = R.color.gray))
                }
            }

        },
        bottomBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth().background(color = colorResource(id = R.color.background))
                    .padding(10.dp, 0.dp),
                horizontalArrangement =Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box (
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (buttonState.value[0].isNotEmpty()){
                        ButtonUI(
                            text =buttonState.value[0],
                            backgroundColor = colorResource(id = R.color.gray),
                            textColor =Color.White,
                        ) {
                            scope.launch {
                                if (pagerState.currentPage > 0) {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            }
                        }
                    }
                }


                Box(modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center) {
                    IndicatorUI(pageSize = pages.size, currentPage = pagerState.currentPage)
                }

                Box(modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ){
                    ButtonUI(
                        text = buttonState.value[1],
                        backgroundColor = colorResource(R.color.dark_green),
                        textColor = Color.White
                    ) {
                        scope.launch {
                            if (pagerState.currentPage < pages.size - 1) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            } else {
                                onFinished()
                            }
                        }
                    }
                }
            }
        }, content = {
            Column(Modifier.padding(it)) {
                HorizontalPager(state = pagerState) { index ->
                    OnboardingGraphUI(onboarding = pages[index])
                }
            }
        })

}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    val navController = rememberNavController()
    OnboardingScreen(navController = navController, onFinished = {})
}