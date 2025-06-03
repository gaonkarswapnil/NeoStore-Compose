package com.example.neostorecompose.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.RestoreFromTrash
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.neostorecompose.data.dto.ProductItemData
import com.example.neostorecompose.ui.viewmodel.CartViewModel
import com.example.neostorecompose.ui.viewmodel.UserViewModel

@Composable
fun CartItem(
    item: ProductItemData,
    quantity: Int,
    addClick: () -> Unit,
    removeClick: () -> Unit,
    deleteItem: () -> Unit,
    disableAdd: Boolean = false,
    disableRemove: Boolean = false
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 10.dp,
                vertical = 2.dp
            ),

    ) {

        Column {


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Image(
                    painter = rememberAsyncImagePainter(model = item.product.productImages),
                    contentDescription = "Image",
                    modifier = Modifier.size(150.dp).padding(10.dp)
                )

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text=item.product.name,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )

                    Text(
                        text=item.product.productCategory,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )

                    Row {
                        Text(
                            text="Quantity",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(20.dp)
                        )

                        IconButtonComp(
                            color = Color.Red,
                            icon = Icons.Filled.Remove,
                            iconDesc = "remove",
                            onClick = {
                                removeClick()
                            },
                            enabled = !disableRemove
                        )

                        Text(
                            text=quantity.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(vertical = 2.dp, horizontal = 20.dp)
                        )

                        IconButtonComp(
                            color = Color.Green,
                            icon = Icons.Filled.Add,
                            iconDesc = "add",
                            onClick = {
                                addClick()
                            },
                            enabled = !disableAdd
                        )
                    }

                    val cost = quantity * item.product.cost

                    Text(
                        text= cost.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }

            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                onClick = {
                    deleteItem()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray.copy(alpha = 0.5f),
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    Icons.Outlined.RestoreFromTrash,
                    contentDescription = "Trash",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(
                    modifier = Modifier.width(5.dp)
                )
                Text(
                    text = "Remove"
                )
            }
        }
    }

}