package com.example.todo_mvvmsushil.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.todo_mvvmsushil.R;
import com.example.todo_mvvmsushil.activities.ViewTodosActivity;
import com.example.todo_mvvmsushil.listeners.OnTodoCompletedListener;
import com.example.todo_mvvmsushil.listeners.OnUpdateSelectListener;
import com.example.todo_mvvmsushil.models.Todo;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<Todo> todos;
    private OnTodoCompletedListener mOnTodoCompletedListener;
    private OnUpdateSelectListener onUpdateSelectListener;

    public TodoAdapter(OnTodoCompletedListener mOnTodoCompletedListener, OnUpdateSelectListener onUpdateSelectListener) {
        this.mOnTodoCompletedListener = mOnTodoCompletedListener;
        this.onUpdateSelectListener = onUpdateSelectListener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.setData(todo);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

        class TodoViewHolder extends RecyclerView.ViewHolder {

        TextView title, datetime;
        CheckBox completed;
        Button isUpdate;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.todoTitle);
            datetime = itemView.findViewById(R.id.todoDateTime);
            completed = itemView.findViewById(R.id.todoCompleted);
            isUpdate = itemView.findViewById(R.id.task_update);

        }



        public void setData(Todo todo) {
            this.title.setText(todo.getTitle());

            String pattern = "hh:mm - MMM dd";

            this.datetime.setText(new SimpleDateFormat(pattern).format(new Date(todo.getCreatedAt())));
            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        Todo completedTodo = todos.get(getAdapterPosition());
                        mOnTodoCompletedListener.todoCompleted(completedTodo);
                    }
                }
            });

            isUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Todo updateTodo = todos.get(getAdapterPosition());
                    onUpdateSelectListener.todoUpdate(updateTodo);
                }
            });
        }


    }

}
