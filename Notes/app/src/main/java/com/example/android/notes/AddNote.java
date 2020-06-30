package com.example.android.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinner;
    private RadioGroup radioGroup;
    private NotesDbHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        dbHelper = new NotesDbHelper(this);
        editTextDescription = findViewById(R.id.editDescription);
        editTextTitle = findViewById(R.id.editTitle);
        spinner = findViewById(R.id.spinnerDayOfWeek);
        radioGroup = findViewById(R.id.radioPriority);
    }

    public void onClickSave(View view) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int day = spinner.getSelectedItemPosition();
        int idRadio = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(idRadio);
        int priority = Integer.parseInt(radioButton.getText().toString());
        if (isFilled(title, description)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, title);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, description);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, day + 1);
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority);
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isFilled(String title, String description){
        return !title.isEmpty() && !description.isEmpty();
    }
}