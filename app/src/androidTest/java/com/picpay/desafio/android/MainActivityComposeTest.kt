package com.picpay.desafio.android

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.ui.mvi.ContactIntent
import com.picpay.desafio.android.ui.mvi.ContactViewModel
import com.picpay.desafio.android.ui.theme.DesafioandroidTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test

@HiltAndroidTest
class MainActivityComposeTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule val composeTestRule = createComposeRule()

    @Inject
    lateinit var myViewModel: ContactViewModel

    @Before
    fun setup() {
        hiltRule.inject()

    }

    @Test
    fun testContactScreen_DisplaysContacts(): Unit = runBlocking{

        composeTestRule.setContent {
            DesafioandroidTheme {
                ContactsScreen(viewModel = myViewModel)
            }
        }

        myViewModel.handleIntent(ContactIntent.LoadContacts)
        delay(20000)

        composeTestRule.onNodeWithText("Contatos").assertExists()
        composeTestRule.onNodeWithTag("CONTACTS_LIST").assertExists()

    }

    @Test
    fun testContactItem_ClickTriggersAction() {
        var clicked = false

        composeTestRule.setContent {
            ContactItem(user = User("id1", "Usuário 1", "user1", "url_img")) {
                clicked = true
            }
        }
        composeTestRule.onNodeWithText("Usuário 1").performClick()
        assert(clicked)
    }
}

