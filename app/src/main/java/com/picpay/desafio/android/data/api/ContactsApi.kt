package com.picpay.desafio.android.data.api

import com.picpay.desafio.android.data.model.User
import retrofit2.http.GET

interface ContactsApi {

    @GET("users/")
    suspend fun getContacts() : List<User>
}