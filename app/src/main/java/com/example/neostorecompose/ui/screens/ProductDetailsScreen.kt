package com.example.neostorecompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.neostorecompose.ui.components.*
import com.example.neostorecompose.ui.theme.OrangePrimary
import com.example.neostorecompose.ui.viewmodel.ProductViewModel
import com.example.neostorecompose.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    productViewModel: ProductViewModel,
    productId: Int
) {
    val productDetailsUiState = productViewModel.productDetailsData.collectAsState().value
    val selectedImageUrl = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(productId) {
        productViewModel.getProductsDetailsData(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Product Details",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
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
    ) { padding ->

        Column(
            modifier = Modifier
                .BackgroundForScreens()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            when (productDetailsUiState) {
                is UiState.Error -> {
                    Text(
                        text = "An error occurred while loading product details.",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                UiState.Loading -> {
                    LoaderComp()
                }

                is UiState.Success -> {
                    val data = productDetailsUiState.data.productDetailsData

                    val category = when (data.product_category_id) {
                        1 -> "Table"
                        2 -> "Chair"
                        3 -> "Sofa"
                        4 -> "Beds"
                        else -> "NA"
                    }

                    if (selectedImageUrl.value == null) {
                        selectedImageUrl.value = data.product_images.firstOrNull()?.image
                    }

                    // Product Name & Category Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            CustomText(
                                text = data.name,
                                style = MaterialTheme.typography.headlineMedium
                            )
                            CustomText(
                                text = "Category: $category",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Row(modifier = Modifier.padding(top = 8.dp)) {
                                CustomText(
                                    text = data.producer,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                RatingBar(rating = data.rating)
                            }
                        }
                    }

                    // Image, Cost & Description Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            ) {
                                CustomText(
                                    text = "Rs.${data.cost}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color.Red
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share button"
                                )
                            }

                            Image(
                                painter = rememberAsyncImagePainter(selectedImageUrl.value),
                                contentDescription = data.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .padding(10.dp)
                            )

                            ProductLazyRow(
                                products = data.product_images,
                                onImageClick = { clickedImage ->
                                    selectedImageUrl.value = clickedImage
                                }
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            CustomText(
                                text = "DESCRIPTION",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            CustomText(
                                text = data.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    // Action Buttons Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            CustomButton(
                                text = "BUY NOW",
                                onClick = { /* Handle buy */ },
                                colors = ButtonDefaults.buttonColors(OrangePrimary)
                            )
                            CustomButton(
                                text = "RATE",
                                onClick = { /* Handle rate */ },
                                colors = ButtonDefaults.buttonColors(Color.Gray)
                            )
                        }
                    }
                }

                else -> {}
            }
        }
    }
}
