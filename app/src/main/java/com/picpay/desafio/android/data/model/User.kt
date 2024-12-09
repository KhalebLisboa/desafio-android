package com.picpay.desafio.android.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("img")
    val img: String? = null,

    @SerializedName("username")
    val username: String? = null
) : Serializable