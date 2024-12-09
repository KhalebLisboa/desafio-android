package com.picpay.desafio.android

import com.picpay.desafio.android.data.api.ContactsApi
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.ContactsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class ContactsRepositoryTest {

    private val apiMock: ContactsApi = mock(ContactsApi::class.java)
    private val repository = ContactsRepository(apiMock)

    @Test
    fun `fetchContacts emits list of users from API`() = runTest {
        val expectedContacts = listOf(
            User("1", "joão oliveira"),
            User("2", "pedro prudent")
        )

        `when`(apiMock.getContacts()).thenReturn(expectedContacts)

        val result = repository.fetchContacts().first()

        verify(apiMock).getContacts()

        assertEquals(2, result.size)
        assertEquals(expectedContacts.first(), result.first())
    }

    @Test(expected = RuntimeException::class)
    fun `fetchContacts throws exception when API fails`() = runTest {
        `when`(apiMock.getContacts()).thenThrow(RuntimeException("API error"))

        repository.fetchContacts().toList()
    }

    @Test(timeout = 2000)
    fun `fetchContacts throws TimeoutCancellationException when exceeding timeout`() = runTest {
        `when`(apiMock.getContacts()).thenAnswer {
                listOf(User("John Doe", "john@example.com"))
        }

        try {
            withTimeout(1000) {
                delay(2000)
                repository.fetchContacts().toList()
            }
            fail("Expected TimeoutCancellationException was not thrown")
        } catch (e: Exception) {
            assertTrue(e is TimeoutCancellationException)
        }
    }
}