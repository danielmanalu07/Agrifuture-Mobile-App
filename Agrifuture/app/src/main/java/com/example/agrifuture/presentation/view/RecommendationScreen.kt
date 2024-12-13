package com.example.agrifuture.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.component.authentication.TextFieldUI
import com.example.agrifuture.presentation.component.botombar.BotomAppBarUI
import com.example.agrifuture.presentation.repository.RecommendationRepository
import com.example.agrifuture.presentation.state.RecommendationState
import com.example.agrifuture.presentation.viewModel.RecommendationVM
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RecommendationScreen(navController: NavController, recommendationVM: RecommendationVM) {

    val recommendationState by recommendationVM.recommendedState.collectAsState()

    LaunchedEffect (recommendationState) {
        if (recommendationState is RecommendationState.Idle) {
            recommendationVM.recommendation()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(R.color.background),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = colorResource(R.color.background))
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowLeft,
                                contentDescription = null,
                                tint = colorResource(R.color.black),
                                modifier = Modifier
                                    .size(40.dp)
                                    .offset(x = -10.dp)
                            )
                        }
                    }
                },
                elevation = 0.dp
            )
        }
    ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                            .background(color = colorResource(R.color.background))
                            .height(250.dp)
                    )
                    Spacer(modifier = Modifier.height(250.dp))
                    when (val state = recommendationState) {
                        is RecommendationState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                        is RecommendationState.Success -> {
                            Text(
                                text = "Hasil Pencarian",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp),
                            )
                            Text(
                                text = state.recommended_pupuk,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth().padding(horizontal = 30.dp)
                            )
                        }
                        is RecommendationState.Error -> {
                            Text(
                                text = state.message,
                                color = Color.Red,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp),
                            )
                        }
                        else -> {
                            Text("Masukkan data untuk mendapatkan rekomendasi.")
                        }
                    }

                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(400.dp)
                        .align(Alignment.Center)
                        .offset(y = -100.dp),
                    elevation = CardDefaults.cardElevation(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.white)
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Title
                        Text(
                            text = "Cari rekomendasi pupuk",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = colorResource(R.color.black)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        OutlinedTextField(
                            value = recommendationVM.jenis_tanah,
                            onValueChange = {
                                recommendationVM.onJenisTanahChange(it)
                                recommendationVM.validateJenisTanah()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            singleLine = true,
                            label = { Text("Jenis Tanah") }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = recommendationVM.jenis_tanaman,
                            onValueChange = {
                                recommendationVM.onJenisTanamanChange(it)
                                recommendationVM.validateJenisTanaman()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            singleLine = true,
                            label = { Text("Jenis Tanaman") }
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(colorResource(R.color.green))
                                .clickable {
                                    if (recommendationVM.validateField()){
                                        recommendationVM.recommendation()
                                    }
                                }
                                .padding(horizontal = 48.dp, vertical = 12.dp)
                        ) {
                            if (recommendationState is RecommendationState.Loading){
                                Text(
                                    text = "Loading",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            } else{
                                Text(
                                    text = "Cari Pupuk",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
}


@Preview(showBackground = true)
@Composable
private fun RecommendationScreenPreview() {
    val navController = rememberNavController()
    val recommendationRepository = RecommendationRepository()
    val recommendationVM = RecommendationVM(recommendationRepository)
    RecommendationScreen(navController = navController, recommendationVM)
}