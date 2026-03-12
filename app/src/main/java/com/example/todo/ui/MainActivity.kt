package com.example.todo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.model.Todo
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.viewmodel.TodoUiState
import com.example.todo.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(modifier = modifier, text = "Loading...")
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Text(modifier = modifier, text = "Error retrieving data from API.")
}

@Composable
fun TodoScreen(modifier: Modifier = Modifier, todoViewModel: TodoViewModel = viewModel()) {
    when (val uiState = todoViewModel.todoUiState) {
        is TodoUiState.Loading -> LoadingScreen(modifier)
        is TodoUiState.Success -> TodoList(modifier, todos = uiState.todos)
        is TodoUiState.Error -> ErrorScreen(modifier)
    }
}

@Composable
fun TodoList(modifier: Modifier, todos: List<Todo>) {
    LazyColumn(
        modifier = modifier.padding(8.dp)
    ) {
        items(todos) { todo ->
            Text(
                text = todo.title,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}
