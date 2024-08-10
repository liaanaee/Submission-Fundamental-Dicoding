package com.dicoding.submissionfundamental.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.submissionfundamental.data.response.DetailUserResponse
import com.dicoding.submissionfundamental.data.retrofit.ApiConfig
import com.dicoding.submissionfundamental.datatambahan.local.DatabaseUser
import com.dicoding.submissionfundamental.datatambahan.local.FavUser
import com.dicoding.submissionfundamental.datatambahan.local.FavUserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _detailGithub = MutableLiveData<DetailUserResponse>()
    val detailGithub: LiveData<DetailUserResponse> = _detailGithub

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _usernameGithub = MutableLiveData<String>()
    val usernameGithub: LiveData<String> = _usernameGithub

    private var userDao: FavUserDao? = null
    private var userData: DatabaseUser? = null

    companion object {
        private const val TAG = "DetailViewModel"
    }

    init {
        userData = DatabaseUser.getDatabase(application)
        userDao = userData?.favUserDao()
    }

    fun detailUserGithub(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailGithub(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailGithub.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun setUsernameGithub(username: String) {
        _usernameGithub.value = username
    }

    fun insertToFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavUser(
                id,
                username,
                avatarUrl)
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkFavo(id: Int) = userDao?.CekUser(id)

    fun deleteFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFav(id)
        }
    }
}
