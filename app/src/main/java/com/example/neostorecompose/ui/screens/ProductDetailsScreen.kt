package com.example.neostorecompose.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.neostorecompose.domain.model.CartRequest
import com.example.neostorecompose.ui.components.BackgroundForScreens
import com.example.neostorecompose.ui.components.CustomButton
import com.example.neostorecompose.ui.components.CustomDialog
import com.example.neostorecompose.ui.components.CustomText
import com.example.neostorecompose.ui.components.LoaderComp
import com.example.neostorecompose.ui.components.ProductLazyRow
import com.example.neostorecompose.ui.components.RatingBar
import com.example.neostorecompose.ui.components.RatingDialog
import com.example.neostorecompose.ui.theme.OrangePrimary
import com.example.neostorecompose.ui.viewmodel.CartViewModel
import com.example.neostorecompose.ui.viewmodel.ProductViewModel
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import com.example.neostorecompose.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    userViewModel: UserViewModel,
    productId: Int
) {
    val context = LocalContext.current
    val productDetailsUiState = productViewModel.productDetailsData.collectAsState().value
    val setProductRatingUiState = productViewModel.setProductRatingState.collectAsState().value


    val accessToken = userViewModel.getAccessToken()
    val selectedImageUrl = remember { mutableStateOf<String?>(null) }
    val showDialog = remember { mutableStateOf(false) }
    val ratingDialog = remember { mutableStateOf(false) }


//    val dashboardUiState = dashboardViewModel.dashboardRes.collectAsState().value

//    val categoryName = if (dashboardUiState is UiState.Success) {
//        val categories = dashboardUiState.data.productData.product_categories
//        val categoryId = data.size
//        categories.find { it.id == categoryId }?.name ?: "Category not found"
//    } else {
//        "Loading category..."
//    }

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
                    IconButton(onClick = { }) {
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
            modifier = Modifier.BackgroundForScreens().padding(padding).verticalScroll(
                rememberScrollState()
            )
        ) {

            when (productDetailsUiState) {

                is UiState.Error -> {
                    Text(
                        text = "ERROR",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
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
                        selectedImageUrl.value = data.product_images[0].image
                    }
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
                                text = "Category: ${category}",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(top = 4.dp)
                            )

                            Row(modifier = Modifier.padding(top = 8.dp)) {
                                CustomText(
                                    text = data.producer,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                RatingBar(
                                    rating = data.rating,
                                    modifier = Modifier
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(modifier = Modifier.padding(8.dp)) {
                                CustomText(
                                    text = "Rs.${data.cost.toString()}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color.Red,
                                    modifier = Modifier
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
                                    .align(Alignment.CenterHorizontally)
                            )

                            ProductLazyRow(
                                products = data.product_images,
                                onImageClick = { clickedImage ->
                                    selectedImageUrl.value = clickedImage
                                }
                            )

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

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            var quantityText by remember { mutableStateOf("1") }

                            if (showDialog.value) {
                                CustomDialog(
                                    title = data.name,
                                    quantity = quantityText.toIntOrNull() ?: 1, // fallback to 1
                                    onDismiss = { newQty ->
                                        quantityText= newQty.toString()
                                        val request = CartRequest(data.id, newQty)
                                        cartViewModel.addToCart(accessToken = accessToken.toString(), request = request)
                                        Log.d("Sample Data", "$newQty")
                                        showDialog.value = false

                                    },
                                    imageUrl = data.product_images[0].image
                                )
                            }
                            CustomButton(
                                text = "Add To Cart",
                                onClick = { showDialog.value = true },
                                colors = ButtonDefaults.buttonColors(OrangePrimary),
                                modifier = Modifier
                                    .weight(1f)
                            )
//                            Spacer(modifier = Modifier.weight(1f))
                            if(ratingDialog.value){
                                var selectedRating by remember { mutableIntStateOf(data.rating) }

                                RatingDialog(
                                    title = data.name,
                                    imageUrl = data.product_images[0].image,
                                    rating = selectedRating,
                                    onDismiss = { newRating ->

                                        selectedRating = newRating

                                        ratingDialog.value = false
                                        productViewModel.setProductRating(data.id,newRating)
                                        Log.d("RatingDialog", "New Rating: $newRating")
                                        Toast.makeText(context,"Rating Submitted Successfully",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                            CustomButton(
                                text = "RATE ",
                                onClick = { ratingDialog.value = true},
                                colors = ButtonDefaults.buttonColors(
                                    Color.Gray
                                ),
                                modifier = Modifier
                                    .weight(1f)
                            )
                        }
                    }
                }

                else -> {

                }
            }
        }

    }

}
