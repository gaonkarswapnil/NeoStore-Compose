package com.example.neostorecompose.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.neostorecompose.R
import com.example.neostorecompose.data.dto.UpdateProfileRequest
import com.example.neostorecompose.ui.components.EditableDatePickerField
import com.example.neostorecompose.ui.components.EditableUserProfileField
import com.example.neostorecompose.ui.components.LoaderComp
import com.example.neostorecompose.ui.navigation.Screens
import com.example.neostorecompose.ui.theme.OrangePrimary
import com.example.neostorecompose.ui.theme.OrangeVariant
import com.example.neostorecompose.ui.viewmodel.DashboardViewModel
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import com.example.neostorecompose.utils.UiState
import com.example.neostorecompose.utils.toBase64

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    dashBoardViewModel: DashboardViewModel,
) {
    val accessToken = userViewModel.getAccessToken() ?: ""
    val userProfileData = dashBoardViewModel.dashboardRes.collectAsState().value
    val updateProfileResponse = userViewModel.updateProfileRes.collectAsState().value

    val context = LocalContext.current
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    var showDialog = remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri.value = uri
    }

    LaunchedEffect(accessToken) {
        if (accessToken.isNotEmpty()) {
            dashBoardViewModel.getDashboard(accessToken)
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Success") },
            text = { Text(text = "Profile updated successfully!") },
            icon = { Icon(Icons.Default.DoneOutline, contentDescription = null) },
            iconContentColor = Color.Green,
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        navController.navigate(Screens.ProfileScreen.route) {
                            popUpTo(Screens.EditProfileScreen.route) { inclusive = true }
                        }
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Update Profile",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OrangePrimary
                )
            )
        }
    ) { innerPadding ->

        when (userProfileData) {
            is UiState.Success -> {
                val userData = userProfileData.data.productData.user_data

                var name by remember { mutableStateOf("${userData.first_name} ${userData.last_name}") }
                var email by remember { mutableStateOf(userData.email ?: "") }
                var phone by remember { mutableStateOf(userData.phone_no ?: "") }
                var gender by remember { mutableStateOf(userData.gender ?: "") }
                var dob by remember { mutableStateOf(userData.dob ?: "") }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(OrangePrimary)
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        shape = RoundedCornerShape(26.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(16.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(16.dp),
                        ) {

                            // Profile Image Box
                            Box {
                                val painter =
                                    selectedImageUri.value?.let { rememberAsyncImagePainter(it) }
                                        ?: if (userData.profile_pic.isNullOrBlank())
                                            painterResource(id = R.drawable.man)
                                        else
                                            rememberAsyncImagePainter(userData.profile_pic)

                                Image(
                                    painter = painter,
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "Profile Image",
                                    modifier = Modifier
                                        .height(200.dp)
                                        .width(200.dp)
                                        .padding(16.dp)
                                )

                                IconButton(
                                    onClick = { galleryLauncher.launch("image/*") },
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .background(
                                            Color.Black.copy(alpha = 0.6f),
                                            shape = RoundedCornerShape(50)
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit Image",
                                        tint = Color.White
                                    )
                                }
                            }

                            // Editable fields
                            EditableUserProfileField(
                                icon = Icons.Default.AccountCircle,
                                value = name,
                                onValueChange = { name = it }
                            )
                            EditableUserProfileField(
                                icon = Icons.Default.Email,
                                value = email,
                                onValueChange = { email = it }
                            )
                            EditableUserProfileField(
                                icon = Icons.Default.Phone,
                                value = phone,
                                onValueChange = { phone = it }
                            )
                            EditableUserProfileField(
                                icon = if (gender.equals("M", true)) Icons.Default.Male else Icons.Default.Female,
                                value = gender,
                                onValueChange = { gender = it }
                            )
                            EditableDatePickerField(
                                icon = Icons.Default.Cake,
                                value = dob,
                                onDateSelected = { dob = it }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            val phoneLong = phone.toLongOrNull()

                            val base64ImageRaw =
                                selectedImageUri.value?.toBase64(context) ?: ""
                            val base64Image = if (base64ImageRaw.isNotEmpty()) {
                                "data:image/jpg;base64,$base64ImageRaw"
                            } else ""

                            val nameParts = name.trim().split(" ")
                            val firstName = nameParts.firstOrNull() ?: ""
                            val lastName = nameParts.drop(1).joinToString(" ")

                            val updateProfileReq = UpdateProfileRequest(
                                profile_pic = base64Image,
                                first_name = firstName,
                                last_name = lastName,
                                email = email,
                                phone_no = phoneLong.toString(),
                                dob = dob
                            )

                            Button(
                                onClick = {
                                    if (name.isBlank() || email.isBlank() || phone.isBlank() || gender.isBlank() || dob.isBlank()) {
                                        return@Button
                                    }
                                    Toast.makeText(context, "CLICKED", Toast.LENGTH_SHORT).show()
                                    userViewModel.updateProfileDetails(accessToken, updateProfileReq)
                                },
                                enabled = updateProfileResponse !is UiState.Loading,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = OrangeVariant,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text(text = "Update Profile Data")
                            }
                        }
                    }


                }
            }

            is UiState.Loading -> {
                LoaderComp()
            }

            is UiState.Error -> {
            }

            else -> {}
        }


        when(updateProfileResponse){
            is UiState.Loading->{
                LoaderComp()
            }
            is UiState.Success->{
                val statusCode = updateProfileResponse.data.status
                LaunchedEffect(updateProfileResponse) {
                    if (statusCode == 200) {
                        showDialog.value = true
                    }
                }
            }
            else ->{}
        }


    }
}
