package com.example.neostorecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neostorecompose.data.dto.DashboardResponse
import com.example.neostorecompose.domain.usecase.DashboardUsecase
import com.example.neostorecompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardUsecase: DashboardUsecase
) :ViewModel() {

    private val _dashboardRes = MutableStateFlow<UiState<DashboardResponse>>(UiState.Idle)
    val dashboardRes : StateFlow<UiState<DashboardResponse>> = _dashboardRes

    fun getDashboard(accessToken:String){
        viewModelScope.launch {
            _dashboardRes.value = UiState.Loading
            try {

                val response = dashboardUsecase.invoke(accessToken)
                if (response.isSuccessful && response.body()!=null){
                    _dashboardRes.value = UiState.Success(response.body()!!)
                }else{
                    _dashboardRes.value = UiState.Error(response.errorBody().toString())
                }
            }catch (e:Exception) {
                _dashboardRes.value = UiState.Error(e.message.toString())
            }
        }
    }

}