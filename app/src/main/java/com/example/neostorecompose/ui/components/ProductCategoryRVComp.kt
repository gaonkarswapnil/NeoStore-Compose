package com.example.neostorecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.neostorecompose.domain.model.ProductCategory

@Composable
fun StaggeredRecyclerView(
    categoryList: List<ProductCategory>,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(categoryList) { category ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        val route  = "productList/${category.id}"
                        navController.navigate(route)
                    }
            ) {
                Text(
                    text = category.name,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
