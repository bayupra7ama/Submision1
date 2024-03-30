package com.example.submision1.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submision1.database.Favorite
import com.example.submision1.database.FavoriteDao
import com.example.submision1.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application){

    private val mFavoriteDao : FavoriteDao
    private val executorService :ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite():LiveData<List<Favorite>> = mFavoriteDao.getAllFavorite()

    fun insert(favorite: Favorite){
        executorService.execute { mFavoriteDao.insert(favorite) }
    }
    fun delete(favorite: Favorite)
    {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }

    fun update(favorite: Favorite)
    {
        executorService.execute { mFavoriteDao.update(favorite) }
    }



}