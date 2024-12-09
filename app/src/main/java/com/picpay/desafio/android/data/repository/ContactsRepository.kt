package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.api.ContactsApi
import com.picpay.desafio.android.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ContactsRepository @Inject constructor(private val api : ContactsApi) {

    private val contactListFlow = MutableStateFlow<List<User>>(emptyList())

    fun getContacts(): Flow<List<User>> = contactListFlow

    suspend fun fetchContacts() {
        val contacts = api.getContacts().map { it }
        contactListFlow.value = contacts
    }
}