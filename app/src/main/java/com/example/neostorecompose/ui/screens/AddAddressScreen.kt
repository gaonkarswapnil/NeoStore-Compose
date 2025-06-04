package com.example.neostorecompose.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.neostorecompose.domain.model.Address
import com.example.neostorecompose.ui.components.BackgroundForScreens
import com.example.neostorecompose.ui.components.CustomButton
import com.example.neostorecompose.ui.components.CustomText
import com.example.neostorecompose.ui.components.LoaderComp
import com.example.neostorecompose.ui.navigation.Screens
import com.example.neostorecompose.ui.navigation.SealedBottomNavItem
import com.example.neostorecompose.ui.theme.OrangePrimary
import com.example.neostorecompose.ui.theme.OrangeVariant
import com.example.neostorecompose.ui.viewmodel.AddressViewModel
import com.example.neostorecompose.utils.UiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAddressScreen(
    navController:NavController,
    addressViewModel : AddressViewModel
) {
    var address by remember { mutableStateOf(TextFieldValue("NeoSOFT Technologies 4th Floor, The Ruby, 29, Senapati Bapat Marg, Dadar (West) Mumbai- 400-028. INDIA.")) }
    var landmark by remember { mutableStateOf(TextFieldValue()) }
    var city by remember { mutableStateOf(TextFieldValue("Mumbai")) }
    var state by remember { mutableStateOf(TextFieldValue("Maharashtra")) }
    var zip by remember { mutableStateOf(TextFieldValue("10066")) }
    var country by remember { mutableStateOf(TextFieldValue("India")) }

    val addresssaveState= addressViewModel.isAddressSaved.collectAsState().value

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Add Address",
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
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CustomText("ADDRESS", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(top = 10.dp),
                placeholder = { Text("Enter address") },
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomText("LANDMARK", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = landmark,
                onValueChange = { landmark = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                placeholder = { Text("Landmark") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    CustomText("CITY", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp, top = 10.dp),
                        placeholder = { Text("Mumbai") }
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    CustomText("STATE", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = state,
                        onValueChange = { state = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, top = 10.dp),
                        placeholder = { Text("Maharashtra") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    CustomText("ZIP CODE", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = zip,
                        onValueChange = { zip = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp, top = 10.dp),
                        placeholder = { Text("10066") }
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    CustomText("COUNTRY", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = country,
                        onValueChange = { country = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, top = 10.dp),
                        placeholder = { Text("India") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangePrimary,
                    contentColor = Color.White
                ),
                onClick = {
                    val newAddress = Address(
                        fullAddress = address.text,
                        landMark = landmark.text,
                        city = city.text,
                        state = state.text,
                        zipcode = zip.text.toLong(),
                        country = country.text
                    )
                    scope.launch {
                        addressViewModel.addAddress(newAddress)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("SAVE ADDRESS")
            }

            // Optional loading indicator
            // CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp).size(30.dp))

            when(addresssaveState){
                is UiState.Loading ->{
                    LoaderComp()
                }
                is UiState.Success ->{
                    val result = addresssaveState.data
                    if(result){
                        navController.navigate(SealedBottomNavItem.cart.route)
                        Toast.makeText(LocalContext.current, "Address Saved Successfully", Toast.LENGTH_SHORT).show()
                    }else{

                    }
                }
                is UiState.Error ->{
                    Toast.makeText(LocalContext.current, "Sorry Try again", Toast.LENGTH_SHORT).show()
                }
                else->{}
            }
        }
    }
}