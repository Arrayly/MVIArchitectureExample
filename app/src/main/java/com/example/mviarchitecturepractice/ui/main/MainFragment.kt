package com.example.mviarchitecturepractice.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.mviarchitecturepractice.R
import com.example.mviarchitecturepractice.model.Todos
import com.example.mviarchitecturepractice.ui.main.state.MainStateEvent
import com.example.mviarchitecturepractice.ui.main.state.MainViewState
import com.example.mviarchitecturepractice.util.ItemDecoration
import com.example.mviarchitecturepractice.util.TodoAdapter
import com.example.mviarchitecturepractice.util.TodoAdapter.Interaction
import java.lang.ClassCastException
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(), Interaction {

    lateinit var viewModel: MainViewModel

    lateinit var dataStateChangeListener: DateStateChangeListener

    lateinit var recyclerViewAdapter: TodoAdapter

    lateinit var recyclerView: RecyclerView

    lateinit var todo_btn: Button
    lateinit var posts_btn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        posts_btn = view.findViewById(R.id.load_posts_btn)
        todo_btn = view.findViewById(R.id.load_todos_btn)
        recyclerView = view.findViewById(R.id.MainFragment_RecyclerView)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            recyclerViewAdapter = TodoAdapter(this@MainFragment)
            recyclerView.addItemDecoration(ItemDecoration(30))
            adapter = recyclerViewAdapter
        }






        viewModel = activity?.run { ViewModelProvider(this).get(MainViewModel::class.java) }
            ?: throw Exception("Invalid activity")

        subscribeObservers()

        //Get list of todos
        todo_btn.setOnClickListener {
            viewModel.setStateEvent(MainStateEvent.getTodosEvent())
        }

        //get lists of posts
        posts_btn.setOnClickListener {
            viewModel.setStateEvent(MainStateEvent.getPostsEvent())
        }
    }

    //RETRIEVING THE ACTUAL DATA FROM OUR SOURCES (E.G. INTERNET/DATABASE)
    fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("DEBUG: DataState: ${dataState}")

            //Handle loading and message
            dataStateChangeListener.onDataStateChange(dataState)

            //Handle Data
            dataState.data?.let { mainViewState ->
                mainViewState.getContentIfNotHandled()?.let {
                    it.posts?.let { list ->
                        viewModel.setPosts(list)
                    }

                    it.todos?.let { list ->
                        viewModel.setTodos(list)
                    }
                }

            }

        })

        //Update the widgetsm
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.posts?.let {
                println("DEBUG: ViewState = ${it}")
            }

            viewState.todos?.let {
                println("DEBUG: ViewState = ${it}")

                recyclerViewAdapter.submitList(it)
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {

            dataStateChangeListener = context as DateStateChangeListener
        } catch (e: ClassCastException) {
            println("DEBUG: ${e}... $context MainActivity must implement DataStateChangeListener")
        }
    }

    override fun onItemSelected(position: Int, item: Todos) {
        println("DEBUG: ${position} ${item}")
    }
}
