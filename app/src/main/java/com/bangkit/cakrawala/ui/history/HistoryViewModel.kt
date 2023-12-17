package com.bangkit.cakrawala.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.cakrawala.data.repository.HistoryRepository
import com.bangkit.cakrawala.data.response.HistoryResponse
import com.bangkit.cakrawala.data.response.HistoryResponseItem
import kotlinx.coroutines.launch

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {
    private val _history = MutableLiveData<HistoryResponse>()
    val history: LiveData<HistoryResponse> = _history

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "UserViewModel"
    }

    val getPaging : LiveData<PagingData<HistoryResponseItem>> = historyRepository.getPaging().cachedIn(viewModelScope)

    fun deleteAll(){
        viewModelScope.launch {
            historyRepository.deleteAll()
        }
    }
}