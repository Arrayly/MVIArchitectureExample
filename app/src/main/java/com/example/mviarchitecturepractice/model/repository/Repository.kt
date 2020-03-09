package com.example.mviarchitecturepractice.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mviarchitecturepractice.api.RetrofitBuilder
import com.example.mviarchitecturepractice.model.Posts
import com.example.mviarchitecturepractice.model.Todos
import com.example.mviarchitecturepractice.ui.main.state.MainViewState
import com.example.mviarchitecturepractice.util.ApiEmptyResponse
import com.example.mviarchitecturepractice.util.ApiErrorResponse
import com.example.mviarchitecturepractice.util.ApiSuccessResponse
import com.example.mviarchitecturepractice.util.DataState
import com.example.mviarchitecturepractice.util.DataState.Companion
import com.example.mviarchitecturepractice.util.GenericApiResponse

object Repository {

    fun getPosts(): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<List<Posts>, MainViewState>(){
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<Posts>>) {

                result.value = DataState.data(
                    data = MainViewState(
                        posts = response.body
                    )
                )

            }

            override fun createCall(): LiveData<GenericApiResponse<List<Posts>>> {
                return RetrofitBuilder.apiService.getPosts()
            }
        }.asLiveData()
    }

    fun getTodos(): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<List<Todos>, MainViewState>(){
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<Todos>>) {

                result.value = DataState.data(
                    data = MainViewState(
                        todos = response.body
                    )
                )

            }

            override fun createCall(): LiveData<GenericApiResponse<List<Todos>>> {
                return RetrofitBuilder.apiService.getTodos()
            }
        }.asLiveData()
    }
}