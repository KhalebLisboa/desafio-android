package com.picpay.desafio.android.ui.mvi

sealed class ContactIntent {
    data object LoadContacts : ContactIntent()
}