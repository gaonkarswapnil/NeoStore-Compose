package com.example.neostorecompose.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.neostorecompose.ui.components.BackgroundForScreens
import com.example.neostorecompose.ui.components.LoaderComp
import com.example.neostorecompose.ui.components.OrderDetailCard
import com.example.neostorecompose.ui.viewmodel.OrderViewModel
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import com.example.neostorecompose.utils.UiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen(
    orderId: Int,
    navController: NavController,
    userViewModel: UserViewModel,
    orderViewModel: OrderViewModel
) {


    val orderState = orderViewModel.fetchOrderRes.collectAsState().value
    val accessToken = userViewModel.getAccessToken() ?: ""
    LaunchedEffect(accessToken) {
        orderViewModel.fetchOneOrderById(accessToken, orderId)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "OrderId : $orderId",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { inner ->

        Box(modifier = Modifier
            .BackgroundForScreens()
            .fillMaxSize()
            .padding(inner), contentAlignment = Alignment.Center) {
            when (orderState) {
                is UiState.Loading -> {
                    LoaderComp()
                }

                is UiState.Success -> {
                    val orders =
                        orderState.data.data.order_details
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(orders) { oneorderdetail ->
                            OrderDetailCard(order = oneorderdetail)
                        }
                    }
                }

                is UiState.Error -> {
                    // Show error message
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Error: ${orderState.message}")
                    }
                }

                else -> {
                    // Show empty state if needed
                }
            }
        }
    }

}