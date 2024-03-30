package com.example.submision1.ui.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submision1.R
import com.example.submision1.data.response.ResponseFollowersItem
import com.example.submision1.ui.model.DetailModel
import com.example.submision1.ui.model.FollowerFollowingModel


class HomeFragment : Fragment() {
    private lateinit var viewModel: DetailModel
    private lateinit var followerFollowingModel: FollowerFollowingModel
    private lateinit var rvView2  :RecyclerView
    private lateinit var progressBar2 : ProgressBar


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        progressBar2 = view.findViewById(R.id.progressBar2)
        rvView2 = view.findViewById(R.id.rvView2)
        viewModel = ViewModelProvider(requireActivity())[DetailModel::class.java]

        followerFollowingModel = ViewModelProvider(requireActivity())[FollowerFollowingModel::class.java]



        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        rvView2.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        rvView2.addItemDecoration(itemDecoration)




    val position = arguments?.getInt(ARG_SECTION_NUMBER) ?: 0
        val userAdapter = FollowersAdapter()
        rvView2.layoutManager = LinearLayoutManager(requireActivity())
        rvView2.adapter = userAdapter


        if (position == 1){
            // Observe changes to the username
            followerFollowingModel.isLoading.observe(viewLifecycleOwner, Observer {
                showLoading(it)
            })
            followerFollowingModel.detailFollower.observe(viewLifecycleOwner, Observer {
                    detailFollower->
                setUserGithub(detailFollower)
            })


           followerFollowingModel.userName.observe(viewLifecycleOwner, Observer {
               username -> followerFollowingModel.findFollowersGithub(username)
           })

        }else{
            followerFollowingModel.isLoading.observe(viewLifecycleOwner, Observer {
                showLoading(it)
            })
            followerFollowingModel.detailUser.observe(viewLifecycleOwner, Observer {
                    detailUser->
                setUserGithub(detailUser)
            })


            followerFollowingModel.userName.observe(viewLifecycleOwner, Observer {
                    username -> followerFollowingModel.findFollowingGithub(username)
            })
        }



    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val TAG = "DetailFragment"
    }

    private fun showLoading(isLoading : Boolean){
        if (isLoading){
            progressBar2.visibility = View.VISIBLE
        }else{
            progressBar2.visibility = View.GONE
        }
    }

//    private fun findFOllowerGithub(username: String) {
//        showLoading(true)
//        val client = ApiClient.getApiService().getFollowers(username)
//        client.enqueue(object : Callback<List<ResponseFollowersItem>>{
//            override fun onResponse(
//                call: Call<List<ResponseFollowersItem>>,
//                response: Response<List<ResponseFollowersItem>>
//            ) {
//                if (response.isSuccessful){
//                    showLoading(false)
//                    val responseBody = response.body()
//                    if (responseBody != null){
//                        setUserGithub(responseBody)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<ResponseFollowersItem>>, t: Throwable) {
//                showLoading(false)
//
//                Log.e(TAG,"onfailure ${t.message}")
//            }
//
//        })
//
//    }

//    private fun findFollowingGithub(username: String) {
//        showLoading(true)
//        val client = ApiClient.getApiService().getFollowing(username)
//        client.enqueue(object : Callback<List<ResponseFollowersItem>>{
//            override fun onResponse(
//                call: Call<List<ResponseFollowersItem>>,
//                response: Response<List<ResponseFollowersItem>>
//            ) {
//                if (response.isSuccessful){
//                    showLoading(false)
//                    val responseBody = response.body()
//                    if (responseBody != null){
//                        setUserGithub(responseBody)
//                    }
//                }else{
//                    Log.e(TAG,"error : ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<List<ResponseFollowersItem>>, t: Throwable) {
//                showLoading(false)
//                Log.e(TAG,"onfailure ${t.message}")
//            }
//
//        })
//
//    }

    private fun setUserGithub(userList: List<ResponseFollowersItem>) {
        val adapter = FollowersAdapter()
        adapter.submitList(userList)
        rvView2.adapter = adapter

    }


}


