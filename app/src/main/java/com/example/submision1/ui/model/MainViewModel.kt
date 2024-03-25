package com.example.submision1.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submision1.data.response.ItemsItem
import com.example.submision1.data.response.ResponseUsersGithub
import com.example.submision1.data.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _user = MutableLiveData<List<ItemsItem>>()
    val user : LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    fun setUserName (username: String){
        _userName.value = username
    }





    companion object{
        private const val TAG = "MainActivity"

    }

    init {
        findUserGithub("Bayu Pratama")
    }

     fun findUserGithub(q : String){

        _isLoading.value = true
        val client = ApiClient.getApiService().getUserGithub(q)
        client.enqueue(object : Callback<ResponseUsersGithub> {
            override fun onResponse(
                call: Call<ResponseUsersGithub>,
                response: Response<ResponseUsersGithub>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _user.value = responseBody.items
                    }else{
                        Log.e(TAG, "onfailure : ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUsersGithub>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"onfailure ${t.message}")
            }

        })
    }


}