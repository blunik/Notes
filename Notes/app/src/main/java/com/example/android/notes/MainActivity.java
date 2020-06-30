package com.example.android.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private final ArrayList<Note> notes = new ArrayList<>();
    private NotesDbHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewNotes = findViewById(R.id.notes_list);
        dbHelper = new NotesDbHelper(this);
        database = dbHelper.getWritableDatabase();
        getData();
        NotesAdapter adapter = new NotesAdapter(notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                int id = notes.get(position).getId();
                String where = NotesContract.NotesEntry._ID + " = ?";
                String[] whereArgs = new String[]{Integer.toString(id)};
                database.delete(NotesContract.NotesEntry.TABLE_NAME,where, whereArgs);
                getData();
                adapter.notifyDataSetChanged();

            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                notes.remove(viewHolder.getAdapterPosition());

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    public void onClickAdd(View view) {
        Intent intent = new Intent(this, AddNote.class);
        startActivity(intent);
    }

    private void getData() {
        String selection = NotesContract.NotesEntry.COLUMN_PRIORITY + " < ?";
        String[] selectionArgs = new String[]{"2"};
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, null, null, null, null, NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK );
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            int dayOfWeek = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
            Note note = new Note(id, title, description, dayOfWeek, priority);
            notes.add(note);
        }
        cursor.close();
    }
}