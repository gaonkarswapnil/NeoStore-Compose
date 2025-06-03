package com.example.neostorecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neostorecompose.data.dto.UpdateProfileRequest
import com.example.neostorecompose.data.dto.UpdateProfileResponse
import com.example.neostorecompose.data.dto.UserLoginResponse
import com.example.neostorecompose.domain.model.UserLoginRequest
import com.example.neostorecompose.domain.model.request.UserRegistrationRequest
import com.example.neostorecompose.domain.model.response.UserRegistrationResponse
import com.example.neostorecompose.domain.usecase.UpdateProfileUseCase
import com.example.neostorecompose.domain.usecase.UserLoginUseCase
import com.example.neostorecompose.domain.usecase.UserRegisterUseCase
import com.example.neostorecompose.utils.TokenManager
import com.example.neostorecompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val registerUseCase: UserRegisterUseCase,
    private val loginUseCase: UserLoginUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _userRegisterState =
        MutableStateFlow<UiState<UserRegistrationResponse>>(UiState.Idle)
    val userState: StateFlow<UiState<UserRegistrationResponse>> = _userRegisterState


    private val _updateProfileRes =
        MutableStateFlow<UiState<UpdateProfileResponse>>(UiState.Idle)
    val updateProfileRes :StateFlow<UiState<UpdateProfileResponse>> = _updateProfileRes

    fun userRegistration(request: UserRegistrationRequest) {

        if (request.password != request.confirmPassword) {
            _userRegisterState.value = UiState.Error(
                message = "Password not matching"
            )
            return
        }

        if (!isValidEmail(request.email)) {
            _userRegisterState.value = UiState.Error("Invalid email format")
            return
        }

        if (!isValidPhone(request.phoneNo.toString())) {
            _userRegisterState.value = UiState.Error("Invalid phone number")
            return
        }

        viewModelScope.launch {
            _userRegisterState.value = UiState.Loading
            try {
                val response = registerUseCase.invoke(request)

                if (response.isSuccessful) {
                    _userRegisterState.value = UiState.Success(
                        data = response.body()!!
                    )
                } else {
                    _userRegisterState.value = UiState.Error("Response is Empty")
                }
            } catch (e: Exception) {
                _userRegisterState.value = UiState.Error(e.message!!)
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(email)
    }

    private fun isValidPhone(phone: String): Boolean {
        return Regex("^\\+?[0-9]{10,13}$").matches(phone)
    }

    //Login User
    private val _userLoginState =
        MutableStateFlow<UiState<UserLoginResponse>>(UiState.Idle)
    val userLoginState: StateFlow<UiState<UserLoginResponse>> = _userLoginState

    fun userLogin(request: UserLoginRequest) {

        if (!isValidEmail(request.email)) {
            _userLoginState.value = UiState.Error("Invalid email format")
            return
        }

        viewModelScope.launch {
            _userLoginState.value = UiState.Loading

            try {
                val response = loginUseCase.invoke(request)
                val accessToken = response.body()!!.user.accessToken
                tokenManager.addAccessToken(accessToken)
                if (response.isSuccessful) {
                    _userLoginState.value = UiState.Success(
                        data = response.body()!!
                    )
                } else {
                    _userLoginState.value = UiState.Error("Response is Empty")
                }
            } catch (e: Exception) {
                _userLoginState.value = UiState.Error(e.message!!)
            }
        }
    }


    fun updateProfileDetails(accessToken:String, updateProfileRequest: UpdateProfileRequest){
        _updateProfileRes.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = updateProfileUseCase.invoke(accessToken, updateProfileRequest)

                if(response.isSuccessful && response.body()!=null){
                    _updateProfileRes.value = UiState.Success(response.body()!!)
                }else{
                    _updateProfileRes.value = UiState.Error(response.errorBody().toString())
                }
            }catch (e:Exception){
                _updateProfileRes.value = UiState.Error(e.message!!)
            }
        }
    }


    fun getAccessToken() = tokenManager.getAccessToken()
}