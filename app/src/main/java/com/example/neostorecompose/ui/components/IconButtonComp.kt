package com.example.neostorecompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconButtonComp(
    color: Color,
    icon: Int,
    iconDesc: String,
    onClick: () -> Unit,
    enabled: Boolean = false
) {

    Image(
        painter = painterResource(icon),
        modifier = Modifier
            .size(25.dp)
            .clickable {
                if(enabled){
                    onClick()
                }
            },
        contentDescription = iconDesc
    )

}