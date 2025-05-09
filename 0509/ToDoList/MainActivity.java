package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTodoItemClickListener {

    private List<TodoItem> todoItems;
    private TodoAdapter adapter;
    private EditText editTextTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기화
        todoItems = new ArrayList<>();
        editTextTask = findViewById(R.id.editTextTask);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        RecyclerView recyclerViewTasks = findViewById(R.id.recyclerViewTasks);

        // 어댑터 설정
        adapter = new TodoAdapter(todoItems, this);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(adapter);

        // 추가 버튼 클릭 리스너
        buttonAdd.setOnClickListener(v -> addTodoItem());

        // 샘플 데이터 추가 (테스트용)
        addSampleData();
    }

    private void addSampleData() {
        todoItems.add(new TodoItem("샘플 할 일 1"));
        todoItems.add(new TodoItem("샘플 할 일 2"));
        adapter.notifyDataSetChanged();
    }

    private void addTodoItem() {
        String taskText = editTextTask.getText().toString().trim();
        if (!taskText.isEmpty()) {
            try {
                TodoItem newItem = new TodoItem(taskText);
                todoItems.add(newItem);
                adapter.notifyItemInserted(todoItems.size() - 1);
                editTextTask.setText("");
                Toast.makeText(this, "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "할 일 추가 중 오류: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "할 일을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClick(int position) {
        try {
            if (position >= 0 && position < todoItems.size()) {
                todoItems.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, todoItems.size());
                Toast.makeText(this, "할 일이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "삭제 중 오류: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckBoxClick(int position, boolean isChecked) {
        try {
            if (position >= 0 && position < todoItems.size()) {
                todoItems.get(position).setCompleted(isChecked);
                adapter.notifyItemChanged(position);
            }
        } catch (Exception e) {
            Toast.makeText(this, "체크박스 처리 중 오류: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
