package com.picpay.desafio.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.ui.mvi.ContactIntent
import com.picpay.desafio.android.ui.mvi.ContactState
import com.picpay.desafio.android.ui.mvi.ContactViewModel
import com.picpay.desafio.android.ui.theme.DesafioandroidTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DesafioandroidTheme {
                    ContactsScreen()
            }
        }
    }
}

@Composable
fun ContactsScreen(viewModel: ContactViewModel = hiltViewModel()){
    val state by viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.handleIntent(ContactIntent.LoadContacts)
    }

    if (state is ContactState.Success) {
        LazyColumn {
            items((state as ContactState.Success).data){ user ->
                ContactItem(user)

            }
        }
    } else{
        CircularProgressIndicator()
    }
}

@Composable
fun ContactItem(user: User) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.colorPrimaryDark))
            .height(64.dp)
    ) {
        AsyncImage(
            model = user.img,
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_round_account_circle),
            modifier = Modifier
                .clip(CircleShape)
                .size(62.dp)
                .padding(vertical = 12.dp)
                .padding(start = 24.dp)
        )

        Column(
            modifier = Modifier.padding(start = 16.dp)
        ){
            Text("${user.username}")
            Text("${user.name}")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DesafioandroidTheme {
//        ContactItem("User")
    }
}