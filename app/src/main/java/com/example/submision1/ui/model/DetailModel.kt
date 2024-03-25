package com.example.submision1.ui.model

import ResponseDetailUsersGithub
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submision1.data.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailModel : ViewModel() {

    companion object{

        const val TAG = "DetailActivity"


    }

    private val _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    fun setUserName (username: String){
        _userName.value = username
    }
    private val _detailUser = MutableLiveData<ResponseDetailUsersGithub>()
    val detailUser : LiveData<ResponseDetailUsersGithub> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading




    init {
        findDetailUser("")
    }

     fun findDetailUser(username : String){
        _isLoading.value = true


        val client = ApiClient.getApiService().getUserDetailGithub(username)
        client.enqueue(object : Callback<ResponseDetailUsersGithub> {
            override fun onResponse(
                call: Call<ResponseDetailUsersGithub>,
                response: Response<ResponseDetailUsersGithub>

            ) {

                if (response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()!!

                    val username = responseBody.login
                    if (username != null) {
                        setUserName(username)
                        _detailUser.value = responseBody

                    }else{
                        Log.e(TAG,"error : ${response.message()}")
                    }
                }


            }

            override fun onFailure(call: Call<ResponseDetailUsersGithub>, t: Throwable) {
                Log.e(TAG,"salah : ${t.message}")
            }

        })
    }
}