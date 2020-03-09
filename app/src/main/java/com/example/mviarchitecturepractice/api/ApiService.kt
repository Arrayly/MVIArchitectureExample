package com.example.mviarchitecturepractice.api

import androidx.lifecycle.LiveData
import com.example.mviarchitecturepractice.model.Posts
import com.example.mviarchitecturepractice.model.Todos
import com.example.mviarchitecturepractice.util.GenericApiResponse
import com.example.mviarchitecturepractice.util.LiveDataCallAdapterFactory
import retrofit2.http.GET

interface ApiService{

    @GET("posts")
    fun getPosts(): LiveData<GenericApiResponse<List<Posts>>>

    @GET("todos")
    fun getTodos():LiveData<GenericApiResponse<List<Todos>>>


}