package com.dicoding.submissionfundamental.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionfundamental.data.response.ItemsItem
import com.dicoding.submissionfundamental.data.retrofit.ApiConfig
import com.dicoding.submissionfundamental.data.response.SearchResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _itemsGithub = MutableLiveData<List<ItemsItem>>()
    val itemsGithub: LiveData<List<ItemsItem>> = _itemsGithub

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        listGithub("a")
    }

    fun listGithub(query: String) {
        _isLoading.value = true
        val  client = ApiConfig.getApiService().getSearch(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _itemsGithub.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}