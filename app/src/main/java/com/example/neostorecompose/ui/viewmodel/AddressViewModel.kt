package com.example.neostorecompose.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neostorecompose.domain.model.Address
import com.example.neostorecompose.domain.repository.AddressRepository
import com.example.neostorecompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository
) :ViewModel() {


    private val _addressList = MutableStateFlow<UiState<List<Address>>>(UiState.Idle)
    val addressList: StateFlow<UiState<List<Address>>> get() = _addressList

    private val _isAddressSaved = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val isAddressSaved: StateFlow<UiState<Boolean>> get() = _isAddressSaved

    private val _isAddressRemoved = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val isAddressRemoved: StateFlow<UiState<Boolean>> get() = _isAddressRemoved

    init {
        getAllAddresses()
    }


    fun getAllAddresses() {
        viewModelScope.launch {
            addressRepository.getAddresses().collect { addresses ->
                _addressList.value  = UiState.Success(addresses)
            }
        }
    }


    fun addAddress(address: Address) {
        viewModelScope.launch {
         try{
         //Refresh the after adding and removing operation
             addressRepository.addAddress(address)
             _isAddressSaved.value =UiState.Success(true)
             getAllAddresses()
         }catch (e :Exception){
             _isAddressSaved.value = UiState.Error("Sorry unable to add address now")
         }
        }
    }

    fun removeAddress(address: Address) {
        viewModelScope.launch {
          try {
              addressRepository.removeAddress(address)
              _isAddressRemoved.value =UiState.Success(true)
              getAllAddresses()
          }catch (e:Exception){
              _isAddressRemoved.value  = UiState.Error("Sorry unable to remove address now")
          }
        }
    }

}