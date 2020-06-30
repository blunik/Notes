package com.example.android.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

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
       if (isFilled(title, description)){
           Note note = new Note(title, description, day, priority);
          viewModel.insertNote(note);
           Intent intent = new Intent(this, MainActivity.class);
           startActivity(intent);
       }
       else {
           Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
       }
    }
    private boolean isFilled(String title, String description){
        return !title.isEmpty() && !description.isEmpty();
    }
}