package com.example.submision1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.submision1.data.response.ResponseFollowersItem
import com.example.submision1.databinding.ItemUserBinding

class FollowersAdapter : ListAdapter<ResponseFollowersItem, FollowersAdapter.MyViewHolder>(DIFF_CALLBACK) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }
    class MyViewHolder(val binding : ItemUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: ResponseFollowersItem){
            binding.user.text = user.login
            binding.imageUser.load(user.avatarUrl){
                transformations(CircleCropTransformation())
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResponseFollowersItem>(){
            override fun areItemsTheSame(
                oldItem: ResponseFollowersItem,
                newItem: ResponseFollowersItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponseFollowersItem,
                newItem: ResponseFollowersItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


    ///    interface callback on click





}