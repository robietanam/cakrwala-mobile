package com.bangkit.cakrawala.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.cakrawala.data.repository.PaymentRepository
import com.bangkit.cakrawala.data.response.AuthResponse
import com.bangkit.cakrawala.data.response.ResponseStatus
import com.bangkit.cakrawala.data.response.BuyPremiumResponse
import com.bangkit.cakrawala.data.response.TransactionHistoryResponse
import com.bangkit.cakrawala.data.response.TransactionHistoryResponseItem
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentViewModel(private val paymentRepository: PaymentRepository) : ViewModel() {

    private val _transaction = MutableLiveData<TransactionHistoryResponse>()
    val transaction: LiveData<TransactionHistoryResponse> = _transaction

    private val _buyPremium = MutableLiveData<BuyPremiumResponse>()
    val buyPremium: LiveData<BuyPremiumResponse> = _buyPremium

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val gson = Gson()
    companion object{
        private const val TAG = "PaymentViewModel"
    }

    val getPaging : LiveData<PagingData<TransactionHistoryResponseItem>> = paymentRepository.getPaging().cachedIn(viewModelScope)

    fun deleteAll(){
        viewModelScope.launch {
            paymentRepository.deleteAll()
        }
    }

    fun buyPremium(id: Int){
        paymentRepository.buyPremium(id).enqueue(object : Callback<BuyPremiumResponse>{
            override fun onResponse(
                call: Call<BuyPremiumResponse>,
                response: Response<BuyPremiumResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _buyPremium.value = response.body()
                } else {
                    val responseData = gson.fromJson(response.errorBody()?.string(), AuthResponse::class.java)
                    _buyPremium.value = BuyPremiumResponse(status = ResponseStatus.Error, message = responseData.message ?: "Terdapat error, mohon coba lagi nanti")
                }
            }

            override fun onFailure(call: Call<BuyPremiumResponse>, t: Throwable) {
                _isLoading.value = false
                val message = t.message ?: "Gagal menjangkau API, mohon coba lagi nanti"
                _buyPremium.value = BuyPremiumResponse(status = ResponseStatus.Error, message = message)
            }

        })
    }
}