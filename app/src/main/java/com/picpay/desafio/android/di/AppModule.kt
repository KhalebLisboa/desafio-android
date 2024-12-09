package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.api.ContactsApi
import com.picpay.desafio.android.data.repository.ContactsRepository
import com.picpay.desafio.android.domain.GetContactsUseCase
import com.picpay.desafio.android.ui.mvi.ContactViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(api: ContactsApi) : ContactsRepository{
        return ContactsRepository(api)
    }

    @Provides
    @Singleton
    fun provideGetContactsUseCase(repository: ContactsRepository) : GetContactsUseCase{
        return GetContactsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideContactViewModel(useCase: GetContactsUseCase) : ContactViewModel{
        return ContactViewModel(useCase)
    }
}