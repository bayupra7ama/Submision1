package com.example.submision1.ui.detail.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.submision1.database.Favorite
import com.example.submision1.repository.FavoriteRepository

//penghubung repository dan activity
class FavoriteAddUpdateViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite){
        mFavoriteRepository.insert(favorite)
    }
    fun update(favorite: Favorite) {
        mFavoriteRepository.update(favorite)
    }
    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }
}