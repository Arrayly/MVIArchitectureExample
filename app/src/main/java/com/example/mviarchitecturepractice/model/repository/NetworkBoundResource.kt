package com.example.mviarchitecturepractice.model.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mviarchitecturepractice.util.ApiEmptyResponse
import com.example.mviarchitecturepractice.util.ApiErrorResponse
import com.example.mviarchitecturepractice.util.ApiSuccessResponse
import com.example.mviarchitecturepractice.util.Constants.Companion.TESTING_NETWORK_DELAY
import com.example.mviarchitecturepractice.util.DataState
import com.example.mviarchitecturepractice.util.GenericApiResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {

        //As soon as class is initialised we want to load a progress bar
        result.value = DataState.loading(true)


        GlobalScope.launch(IO){

            //For testing
            delay(TESTING_NETWORK_DELAY)

            withContext(Main){
                val apiResponse = createCall()
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)

                    handleNetworkCall(response)
                }
            }
        }
    }

    fun handleNetworkCall(response: GenericApiResponse<ResponseObject>){

        when(response){
            is ApiSuccessResponse ->{
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse ->{
                println("DEBUG: NetworkBoundResource: ${response.errorMessage}")
                onReturnError(response.errorMessage)
            }
            is ApiEmptyResponse ->{
                println("DEBUG: NetworkBoundResource: Request returned NOTHING (HTTP 204)")
                onReturnError("HTTP 204. Returned NOTHING.")
            }
        }
    }

    fun onReturnError(message: String){
        result.value = DataState.error(message)
    }

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>


    //RETURN A LIVE DATA OBJECT instead of a boundresource
    fun asLiveData() = result as LiveData<DataState<ViewStateType>>
}