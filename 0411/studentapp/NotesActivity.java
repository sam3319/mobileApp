package com.example.studentapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    private EditText editTextNote;
    private Button btnAddNote;
    private ListView listViewNotes;
    private ArrayList<String> notesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        editTextNote = findViewById(R.id.editTextNote);
        btnAddNote = findViewById(R.id.btnAddNote);
        listViewNotes = findViewById(R.id.listViewNotes);

        notesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(adapter);

        // 노트 추가 버튼 클릭 리스너
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = editTextNote.getText().toString();
                if (!note.isEmpty()) {
                    notesList.add(note);
                    adapter.notifyDataSetChanged();
                    editTextNote.setText("");
                }
            }
        });
    }
}
