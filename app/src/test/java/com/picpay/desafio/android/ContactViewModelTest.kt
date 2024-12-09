package com.picpay.desafio.android

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.GetContactsUseCase
import com.picpay.desafio.android.ui.mvi.ContactIntent
import com.picpay.desafio.android.ui.mvi.ContactState
import com.picpay.desafio.android.ui.mvi.ContactViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.lang.NullPointerException
import kotlin.test.Test

@ExperimentalCoroutinesApi
class ContactViewModelTest {

    private lateinit var viewModel: ContactViewModel
    private val getContactsUseCase: GetContactsUseCase = mock(GetContactsUseCase::class.java)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ContactViewModel(getContactsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verify state changes to Success when contacts are fetched`() = runTest {
        val mockContacts = listOf(User("1", "joão oliveira"))
        `when`(getContactsUseCase.execute()).thenReturn(flowOf(mockContacts))

        viewModel.handleIntent(ContactIntent.LoadContacts)

        advanceTimeBy(10000)

        assertTrue(viewModel.state.value is ContactState.Success)
        assertEquals((viewModel.state.value as ContactState.Success).data, mockContacts)
    }

    @Test
    fun `verify state changes to Error when exception occurs`() = runTest {
        `when`(getContactsUseCase.execute()).thenThrow(RuntimeException("Erro na API"))

        viewModel.handleIntent(ContactIntent.LoadContacts)

        advanceTimeBy(10000)

        assertTrue(viewModel.state.value is ContactState.Error)
        assertEquals((viewModel.state.value as ContactState.Error).message, "Erro na API")
    }

    @Test
    fun `verify if Error state returns default message`() = runTest {
        `when`(getContactsUseCase.execute()).thenThrow(NullPointerException(null))

        viewModel.handleIntent(ContactIntent.LoadContacts)

        advanceTimeBy(10000)

        assertTrue(viewModel.state.value is ContactState.Error)
        assertEquals((viewModel.state.value as ContactState.Error).message, "Não foi possível obter a lista de contatos")
    }
}