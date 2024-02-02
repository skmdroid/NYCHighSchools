package com.example.nychighschools.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nychighschools.model.remote.Repository
import com.example.nychighschools.model.remote.ResultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _data = MutableStateFlow<List<ResultItem>>(emptyList())
    val data: StateFlow<List<ResultItem>> get() = _data

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val dataTemp = repository.getData()
                if (dataTemp.isSuccessful && dataTemp.body() != null) {
                    _data.value = dataTemp.body() as List<ResultItem>
                } else {
                    _error.value = dataTemp.message()
                }
                _isLoading.value = false
            } catch (ex: Exception) {
                _error.value = ex.message
                Log.d("Test123", ex.toString())
                _isLoading.value = false
            }
        }
    }
}