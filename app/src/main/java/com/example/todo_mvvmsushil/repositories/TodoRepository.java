package com.example.todo_mvvmsushil.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import com.example.todo_mvvmsushil.dao.TodoDao;
import com.example.todo_mvvmsushil.databases.TodoDatabase;
import com.example.todo_mvvmsushil.models.Todo;
import com.example.todo_mvvmsushil.tasks.Tasks;

public class TodoRepository {
    private TodoDao dao;
    private TodoDatabase todoDatabase;
    private LiveData<List<Todo>> todos;

    public TodoRepository(Application application) {
        todoDatabase = TodoDatabase.getInstance(application);
        dao = todoDatabase.getDao();
        todos = dao.getTodos();
    }

    public void addTodo(Todo todo) {
        new Tasks.AddTodo(dao).execute(todo);
    }

    public void updateTodo(Todo todo) {
        new Tasks.UpdateTodo(dao).execute(todo);
    }

    public void deleteTodo(Todo todo) {
        new Tasks.DeleteTodo(dao).execute(todo);
    }

    public void deleteAllTodos() {
        new Tasks.DeleteAllTodos(dao).execute();
    }

    public void setCompleted(String id) {
        new Tasks.SetTodoCompleted(dao).execute(id);
    }

    public void setUncompleted(String id) {
        new Tasks.SetTodoUncompleted(dao).execute(id);
    }

    public void setLate(String id) {
        new Tasks.SetTodoLate(dao).execute(id);
    }

    public LiveData<List<Todo>> getTodosByCategory(String category) {

        LiveData<List<Todo>> todos = null;

        try {
            Tasks.GetTodosByCategory getTodosByCategory = new Tasks.GetTodosByCategory(dao);
            todos = getTodosByCategory.execute(category).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return todos;

    }

    public LiveData<List<Todo>> getTodos() {
        return todos;
    }

}
