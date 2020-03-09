package com.example.mviarchitecturepractice.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Posts(

    @Expose
    @SerializedName("body")
    val body: String,

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
        return "Posts(body='$body', id=$id, title='$title', userId=$userId)"
    }
}