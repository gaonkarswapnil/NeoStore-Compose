package com.example.neostorecompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.neostorecompose.ui.components.BackgroundForScreens
import com.example.neostorecompose.ui.components.LoaderComp
import com.example.neostorecompose.ui.components.ProductItem
import com.example.neostorecompose.ui.theme.OrangePrimary
import com.example.neostorecompose.ui.viewmodel.ProductViewModel
import com.example.neostorecompose.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productViewModel: ProductViewModel,
    categoryId: Int
) {

    val productListUiState = productViewModel.productList.collectAsState().value

    LaunchedEffect(categoryId) {
        productViewModel.getProductList(categoryId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Category",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* handle back */ }) {
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
    ) {inner ->
        when(productListUiState){
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
                val data = productListUiState.data.productItem

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .BackgroundForScreens()
                        .padding(inner)
                ) {
                    items(data){ productItem ->
                        ProductItem(productItem)
                    }
                }
            }
            else -> {

            }
        }

    }

}