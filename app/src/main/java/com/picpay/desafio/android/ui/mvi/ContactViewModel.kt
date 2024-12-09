package com.picpay.desafio.android.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.GetContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            getContactsUseCase.fetchContacts()
        }
    }

    private val _state = MutableStateFlow<ContactState>(ContactState.Error(message = "Carregamento Inicial"))
    val state: StateFlow<ContactState> = _state.asStateFlow()

    fun handleIntent(intent: ContactIntent) {
        when (intent) {
            is ContactIntent.LoadContacts -> loadContacts()
        }
    }

    private fun loadContacts() {
        viewModelScope.launch {
            _state.value = ContactState.Loading
            try {
                getContactsUseCase.execute().collect { contacts ->
                    _state.value = ContactState.Success(data = contacts)
                }
            } catch (e: Exception) {
                _state.value = ContactState.Error(
                    message = e.localizedMessage ?: "Não foi possível obter a lista de contatos"
                )
            }
        }
    }
}