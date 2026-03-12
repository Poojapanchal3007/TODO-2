package com.example.todo.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.model.Todo
import com.example.todo.model.TodosApi
import kotlinx.coroutines.launch

sealed interface TodoUiState {
    data class Success(val todos: List<Todo>) : TodoUiState
    object Error : TodoUiState
    object Loading : TodoUiState
}

class TodoViewModel : ViewModel() {
    var todoUiState: TodoUiState by mutableStateOf(TodoUiState.Loading)
        private set

    init {
        getTodosList()
    }

    private fun getTodosList() {
        viewModelScope.launch {
            var todosApi: TodosApi? = null
            try {
                todosApi = TodosApi.getInstance()
                todoUiState = TodoUiState.Success(todosApi.getTodos())
            } catch (e: Exception) {
                Log.d("TODOVIEWMODEL", e.message.toString())
                todoUiState = TodoUiState.Error
            }
        }
    }
}