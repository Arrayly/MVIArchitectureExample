package com.example.mviarchitecturepractice.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mviarchitecturepractice.model.Posts
import com.example.mviarchitecturepractice.model.Todos
import com.example.mviarchitecturepractice.model.repository.Repository
import com.example.mviarchitecturepractice.ui.main.state.MainStateEvent
import com.example.mviarchitecturepractice.ui.main.state.MainStateEvent.*
import com.example.mviarchitecturepractice.ui.main.state.MainViewState
import com.example.mviarchitecturepractice.util.AbsentLiveData
import com.example.mviarchitecturepractice.util.DataState

class MainViewModel : ViewModel() {

    //Structured the same as the google samples
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()

    //Return the object
    val viewState: LiveData<MainViewState>
        get() = _viewState

    //Listening for the change in state of _viewState mutable live data object, if changed,
    //code below will be executed
    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(it)

            }
        }

    // we handle the different state events here

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        when (stateEvent) {

            is getPostsEvent -> {
                return Repository.getPosts()
            }

            is getTodosEvent -> {
                return Repository.getTodos()
            }

            is None -> {
                return AbsentLiveData.create()
            }
        }
    }

    fun setPosts(posts: List<Posts>) {
        //get current state
        val state = getCurrentViewStateOrNew()
        state.posts = posts
        _viewState.value = state
    }

    fun setTodos(toDos: List<Todos>) {
        //get current state
        val state = getCurrentViewStateOrNew()
        state.todos = toDos
        _viewState.value = state
    }

    //CHECK IF viewState is not null, return a new viewstate if it is
    fun getCurrentViewStateOrNew(): MainViewState {
        val value = viewState.value?.let {
            it
        } ?: MainViewState()
        return value
    }

    fun setStateEvent(event: MainStateEvent){
        _stateEvent.value = event
    }
}