package com.picpay.desafio.android.ui.mvi

import com.picpay.desafio.android.data.model.User

sealed class ContactState {
    data class Success(val data: List<User>) : ContactState()
    data object Loading : ContactState()
    data class Error(val message: String) : ContactState()
}