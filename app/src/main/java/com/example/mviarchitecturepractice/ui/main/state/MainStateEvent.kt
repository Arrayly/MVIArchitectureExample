package com.example.mviarchitecturepractice.ui.main.state

sealed class MainStateEvent {

    class getPostsEvent : MainStateEvent()

    class getTodosEvent : MainStateEvent()

    class None: MainStateEvent()
}