package com.example.mviarchitecturepractice.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mviarchitecturepractice.R
import com.example.mviarchitecturepractice.R.layout
import com.example.mviarchitecturepractice.util.DataState
import kotlinx.android.synthetic.main.activity_main.view.fragment_containerView

class MainActivity : AppCompatActivity()
    , DateStateChangeListener {

    lateinit var viewModel: MainViewModel
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        progressBar = findViewById(R.id.MainActivity_pBar)

        loadFragment()
    }

    private fun loadFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_containerView, MainFragment(), "MainFragment")
            .commit()
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    private fun handleDataStateChange(dataState: DataState<*>?) {
        dataState?.let {
            //Handle loading
            if (dataState.loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }

            //Handle Message
            dataState?.message.let { event ->
                event?.getContentIfNotHandled()?.let {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                }

            }
        }
    }
}
