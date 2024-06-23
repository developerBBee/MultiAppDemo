package com.example.second.ui

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.data.User
import com.example.common.viewmodels.UserViewModel
import com.example.second.ui.theme.MultiAppDemoTheme
import com.example.second.ui.theme.Typography
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiAppDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding),
                        name = "Second App",
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    name: String,
    viewModel: UserViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getUsers()
    }

    val state by viewModel.uiStateFlow.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }

        if (state.error.isNotEmpty()) {
            AlertDialog(
                text = { Text(text = state.error) },
                onDismissRequest = { viewModel.clearError() },
                confirmButton = { viewModel.clearError() }
            )
        }

        SecondContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp),
            name = name,
            users = state.users,
        )
    }
}

@Composable
fun SecondContent(
    modifier: Modifier = Modifier,
    name: String,
    users: List<User>,
    viewModel: UserViewModel = hiltViewModel(),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello $name!",
            modifier = Modifier
                .clip(shapes.medium)
                .background(color = colorScheme.secondary)
                .padding(24.dp)
                .clickable {
                    viewModel.saveUser(
                        name = "TestUser${users.size + 1}",
                        email = "hoge${users.size + 1}@gmail.com"
                    )
                },
            color = colorScheme.onSecondary,
            style = Typography.headlineLarge
        )

        LazyColumn {
            items(users) {
                Row {
                    Text(modifier = Modifier.padding(10.dp), text = it.name)
                    Text(modifier = Modifier.padding(10.dp), text = it.email)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MultiAppDemoTheme {
        Greeting(name = "Android")
    }
}