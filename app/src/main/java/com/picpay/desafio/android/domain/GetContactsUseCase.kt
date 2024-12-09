package com.picpay.desafio.android.domain

import com.picpay.desafio.android.core.UseCase
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(private val repository: ContactsRepository) :
    UseCase<List<User>>() {

    override suspend fun execute(): Flow<List<User>> = repository.fetchContacts()

}