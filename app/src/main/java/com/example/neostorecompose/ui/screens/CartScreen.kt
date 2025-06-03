package com.example.neostorecompose.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.neostorecompose.domain.model.CartRequest
import com.example.neostorecompose.ui.components.BackgroundForScreens
import com.example.neostorecompose.ui.components.CartItem
import com.example.neostorecompose.ui.components.LoaderComp
import com.example.neostorecompose.ui.viewmodel.CartViewModel
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import com.example.neostorecompose.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartListScreen(navHostController: NavHostController) {

    val userViewModel: UserViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()

    val accessToken = userViewModel.getAccessToken()

    LaunchedEffect(accessToken) {
        accessToken?.let { cartViewModel.getProductList(it) }
    }

    val cartResponse = cartViewModel.productList.collectAsState().value
    val cartItemsState = remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "My Cart",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { inner ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .BackgroundForScreens()
                .padding(inner)
        ) {
            // Scrollable list with bottom padding to avoid overlap

            when (cartResponse) {
                is UiState.Error -> {
                    Text(
                        text = "ERROR",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                is UiState.Loading -> LoaderComp()

                is UiState.Success -> {
                    val data = cartResponse.data.productItem

                    LaunchedEffect(Unit) {
                        val initialQuantities = data.associate { it.productId to it.quantity }
                        cartItemsState.value = initialQuantities
                    }

                    val totalPrice = cartItemsState.value.entries.sumOf { (productId, quantity) ->
                        val product = data.find { it.productId == productId }?.product
                        quantity * (product?.cost ?: 0)
                    }


                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 80.dp) // Enough padding to avoid overlap
                    ) {
                        items(data){item ->

                            val quantity = cartItemsState.value[item.productId] ?: item.quantity

                            CartItem(
                                item = item,
                                quantity = quantity,
                                addClick = {
                                    val newQty = quantity + 1
                                    if (newQty <= 8) {
                                        cartItemsState.value = cartItemsState.value.toMutableMap().apply {
                                            put(item.productId, newQty)
                                        }
                                        cartViewModel.editCartItems(
                                            accessToken!!,
                                            CartRequest(item.productId, newQty)
                                        )
                                    }
                                },
                                removeClick = {
                                    val newQty = maxOf(1, quantity - 1)
                                    cartItemsState.value = cartItemsState.value.toMutableMap().apply {
                                        put(item.productId, newQty)
                                    }
                                    cartViewModel.editCartItems(
                                        accessToken!!,
                                        CartRequest(item.productId, newQty)
                                    )
                                },
                                deleteItem = {
                                    cartItemsState.value = cartItemsState.value.toMutableMap().apply {
                                        remove(item.productId)
                                    }

                                    cartViewModel.deleteCartItem(
                                        accessToken!!,
                                        item.productId
                                    )
//                                    cartViewModel.getProductList(accessToken)
                                },
                                disableAdd = quantity >= 8,
                                disableRemove = quantity <= 1
                            )
                        }
                    }

                    // Bottom price text (left)
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 10.dp, bottom = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$ ${totalPrice}",
                            fontSize = 26.sp
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Info",
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Bottom button (right)
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 10.dp, bottom = 20.dp)
                    ) {
                        Button(onClick = {}) {
                            Text(
                                text = "Place Order",
                                fontSize = 16.sp
                            )
                        }
                    }

                }

                else -> {}
            }

        }


    }
}