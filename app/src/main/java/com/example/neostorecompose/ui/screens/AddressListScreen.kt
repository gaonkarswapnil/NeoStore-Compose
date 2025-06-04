package com.example.neostorecompose.ui.screens

import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.neostorecompose.domain.model.Address

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.neostorecompose.ui.components.AddressCardComp
import com.example.neostorecompose.ui.components.LoaderComp
import com.example.neostorecompose.ui.navigation.Screens
import com.example.neostorecompose.ui.viewmodel.AddressViewModel
import com.example.neostorecompose.ui.viewmodel.OrderViewModel
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import com.example.neostorecompose.utils.UiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressListScreen(
    navController: NavController,
    addressViewModel: AddressViewModel,
    orderViewModel: OrderViewModel,
    userViewModel: UserViewModel,
//    onPlaceOrderClick: (Address) -> Unit
) {

    val accessToken = userViewModel.getAccessToken()

    val orderState = orderViewModel.orderRes.collectAsState().value
    val showLoader = remember { mutableStateOf(false) }

    LaunchedEffect(accessToken) {
        addressViewModel.getAllAddresses()
    }

    val addressList = addressViewModel.addressList.collectAsState().value
    var selectedAddress by remember { mutableStateOf<Address?>(null) }
    val showToast = remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shipping Address", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    val address = selectedAddress?.fullAddress ?: ""
                    if(!accessToken.isNullOrBlank() && selectedAddress!=null){
                        orderViewModel.orderProduct(accessToken, address)
                    }
                    showLoader.value = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("Place Order", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    ) { padding ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .padding(bottom = 80.dp)
//        ) {

        val context = LocalContext.current
        if(showToast.value){
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Successfully Placed Order", Toast.LENGTH_SHORT).show()
            }
        }

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Shipping Address",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Button(
                        onClick = {
                            navController.navigate(Screens.AddAddressScreen.route)
                        }
                    ) {
                        Text("Add Address")
                    }
                }

                if(showLoader.value){
                    LoaderComp()
                }

                when(addressList){
                    is UiState.Loading->{
                        LoaderComp()
                    }
                    is UiState.Success->{

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp)
                        ) {
                            items(addressList.data) { address ->
                                AddressCardComp(
                                    address = address,
                                    isSelected = address == selectedAddress,
                                    onSelected = {
                                        selectedAddress = address
                                    }
                                )
                            }
                        }
                    }
                    is UiState.Error->{}
                    else->{}
                }

                when(orderState){
                    is UiState.Loading ->{
                        LoaderComp()
                    }
                    is UiState.Success ->{
                        val result = orderState.data.status

                        if(result==200){
                            LaunchedEffect(Unit) {
                                showToast.value = true
                            }
                            navController.navigate(Screens.OrderListScreen.route)

                        }
                    }
                    is UiState.Error ->{}
                    else->{}
                }
//            }
        }

    }
}
