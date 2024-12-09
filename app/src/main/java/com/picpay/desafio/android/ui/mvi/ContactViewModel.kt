package com.picpay.desafio.android.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.GetContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _state = MutableStateFlow<ContactState>(ContactState.Loading)
    val state: StateFlow<ContactState> = _state.asStateFlow()

    fun handleIntent(intent: ContactIntent) {
        when (intent) {
            is ContactIntent.LoadContacts -> loadContacts()
        }
    }

    private fun loadContacts() {
        viewModelScope.launch {
            _state.value = ContactState.Loading
            delay(2000)
            try {
                getContactsUseCase.execute().collectLatest { contacts ->
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