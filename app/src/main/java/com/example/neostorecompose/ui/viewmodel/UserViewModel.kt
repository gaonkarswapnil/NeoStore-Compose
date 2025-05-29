package com.example.neostorecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neostorecompose.domain.model.request.UserRegistrationRequest
import com.example.neostorecompose.domain.model.response.UserRegistrationResponse
import com.example.neostorecompose.domain.usecase.UserRegisterUseCase
import com.example.neostorecompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val registerUseCase: UserRegisterUseCase,
) : ViewModel() {

    private val _userRegisterState =
        MutableStateFlow<UiState<UserRegistrationResponse>>(UiState.Idle)
    val userState: StateFlow<UiState<UserRegistrationResponse>> = _userRegisterState

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

                if (response.isSuccessful){
                    _userRegisterState.value = UiState.Success(
                        data = response.body()!!
                    )
                } else{
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


}