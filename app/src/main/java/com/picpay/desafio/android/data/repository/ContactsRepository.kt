package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.api.ContactsApi
import com.picpay.desafio.android.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContactsRepository @Inject constructor(private val api : ContactsApi) {

    suspend fun fetchContacts() : Flow<List<User>>{
        return flow {
            val contacts = api.getContacts().map { it }
            emit(contacts)
        }
    }
}