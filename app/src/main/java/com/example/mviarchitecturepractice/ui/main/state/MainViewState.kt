package com.example.mviarchitecturepractice.ui.main.state

import com.example.mviarchitecturepractice.model.Posts
import com.example.mviarchitecturepractice.model.Todos

data class MainViewState(


    var posts: List<Posts>? = null,
    var todos: List<Todos>? = null

) {

}