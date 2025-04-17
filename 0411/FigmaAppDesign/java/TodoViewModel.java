package com.example.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Todo>> todos = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Todo> selectedTodo = new MutableLiveData<>();

    public TodoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Todo>> getTodos() {
        return todos;
    }

    public LiveData<Todo> getSelectedTodo() {
        return selectedTodo;
    }

    public void addTodo(Todo todo) {
        List<Todo> currentTodos = todos.getValue();
        if (currentTodos != null) {
            currentTodos.add(todo);
            todos.setValue(currentTodos);
        }
    }

    public void updateTodo(Todo todo) {
        List<Todo> currentTodos = todos.getValue();
        if (currentTodos != null) {
            for (int i = 0; i < currentTodos.size(); i++) {
                if (currentTodos.get(i).getId().equals(todo.getId())) {
                    currentTodos.set(i, todo);
                    break;
                }
            }
            todos.setValue(currentTodos);
        }
    }

    public void deleteTodo(Todo todo) {
        List<Todo> currentTodos = todos.getValue();
        if (currentTodos != null) {
            currentTodos.removeIf(t -> t.getId().equals(todo.getId()));
            todos.setValue(currentTodos);
        }
    }

    public void selectTodo(Todo todo) {
        selectedTodo.setValue(todo);
    }

    public void clearSelectedTodo() {
        selectedTodo.setValue(null);
    }
} 