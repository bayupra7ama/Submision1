package com.example.submision1.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submision1.database.Favorite
import com.example.submision1.repository.FavoriteRepository

class FavoriteViewModel(application: Application):ViewModel() {
    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorite():LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()
    fun update(favorite: Favorite) {
        mFavoriteRepository.update(favorite)
    }
    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }

}