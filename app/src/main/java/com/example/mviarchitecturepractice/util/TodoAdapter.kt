package com.example.mviarchitecturepractice.util

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mviarchitecturepractice.R
import com.example.mviarchitecturepractice.model.Todos
import kotlinx.android.synthetic.main.todo_item.view.blog_title

class TodoAdapter(private val interaction: Interaction? = null) :


    ListAdapter<Todos, TodoAdapter.TodoViewHolder>(TodosDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TodoViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item, parent, false), interaction
    )

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TodoViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView), OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            interaction?.onItemSelected(adapterPosition, getItem(adapterPosition))
        }

        fun bind(item: Todos) = with(itemView) {
            itemView.blog_title.text = item.title
        }
    }

    interface Interaction {

        fun onItemSelected(position: Int, item: Todos)
    }

    private class TodosDiffCallback : DiffUtil.ItemCallback<Todos>() {
        override fun areItemsTheSame(
            oldItem: Todos,
            newItem: Todos
        ): Boolean {

            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(
            oldItem: Todos,
            newItem: Todos
        ): Boolean {
            return oldItem == newItem
        }
    }
}