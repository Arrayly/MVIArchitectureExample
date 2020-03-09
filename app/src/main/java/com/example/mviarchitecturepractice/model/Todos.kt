package com.example.mviarchitecturepractice.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Todos(

    @Expose
    @SerializedName("completed")
    val completed: Boolean,

    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("title")
    val title: String,

    @Expose
    @SerializedName("userId")
    val userId: Int

) {

    override fun toString(): String {
        return "Todos(completed=$completed, id=$id, title='$title', userId=$userId)"
    }
}