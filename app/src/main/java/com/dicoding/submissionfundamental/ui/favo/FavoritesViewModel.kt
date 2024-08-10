package com.dicoding.submissionfundamental.ui.favo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.submissionfundamental.datatambahan.local.DatabaseUser
import com.dicoding.submissionfundamental.datatambahan.local.FavUser
import com.dicoding.submissionfundamental.datatambahan.local.FavUserDao

class FavoritesViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavUserDao?
    private var userData: DatabaseUser?

    init {
        userData = DatabaseUser.getDatabase(application)
        userDao = userData?.favUserDao()
    }

    fun getFavUser(): LiveData<List<FavUser>>?{
        return userDao?.getFavUser()!!
    }

    companion object {
        private const val Tag = "DetailViewModel"
    }
}