package com.example.mviarchitecturepractice.ui.main

import com.example.mviarchitecturepractice.util.DataState


interface DateStateChangeListener {
    fun onDataStateChange(dataState: DataState<*>?)
}