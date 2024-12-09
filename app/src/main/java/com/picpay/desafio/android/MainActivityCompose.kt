package com.picpay.desafio.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.ui.mvi.ContactIntent
import com.picpay.desafio.android.ui.mvi.ContactState
import com.picpay.desafio.android.ui.mvi.ContactViewModel
import com.picpay.desafio.android.ui.theme.DesafioandroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DesafioandroidTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.colorPrimaryDark))
                ) {
                    ContactsScreen()
                }
            }
        }
    }
}

@Composable
fun ContactsScreen(viewModel: ContactViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        Text(
            "Contatos",
            style = TextStyle(fontSize = 28.sp, color = Color.White, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 48.dp, start = 24.dp)
        )
        ContactList(state) {
            viewModel.handleIntent(ContactIntent.LoadContacts)
        }
    }
}

@Composable
fun ContactList(state: ContactState, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(colorResource(R.color.colorPrimaryDark))
            .fillMaxWidth()
    ) {
        when (state) {
            is ContactState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    items(state.data) { user ->
                        ContactItem(user, onClick)
                    }
                }
            }
            is ContactState.Error -> {
                ErrorStateMessage(state.message) {
                    onClick()
                }
            }
            else -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(top = 24.dp),
                    color = colorResource(R.color.colorAccent)
                )
            }
        }
    }
}

@Composable
fun ContactItem(user: User, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.colorPrimaryDark))
            .height(64.dp)
            .padding(start = 24.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = user.img,
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_round_account_circle),
            modifier = Modifier
                .clip(CircleShape)
                .size(52.dp)

        )

        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text("${user.username}", style = TextStyle(color = Color.White))
            Text("${user.name}", style = TextStyle(color = colorResource(R.color.colorDetail)))
        }
    }
}

@Composable
fun ErrorStateMessage(message: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(message, style = TextStyle(color = Color.White))

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DesafioandroidTheme {
        ErrorStateMessage("Não foi possível concluir") {}
    }
}