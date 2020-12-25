package com.george.devil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class NewProjectActivity extends AppCompatActivity {

    TextInputLayout textField_name_proj, textField_description_proj, textField_topic;
    SwitchMaterial switchPrivate_proj;

    private static final String TAG = "newProjectActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar_new_project1);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        textField_name_proj = findViewById(R.id.textField_name_proj);
        textField_description_proj = findViewById(R.id.textField_description_proj);
        textField_topic = findViewById(R.id.textField_topic_new);

        switchPrivate_proj = findViewById(R.id.switchPrivate_proj);

        MaterialAutoCompleteTextView autoCompleteTextView = findViewById(R.id.topic_auto_new_proj);

        String[] items = new String[] {
                "Biology", "Chemistry", "Economics", "English",
                "Engineering/Construction","Geography", "History",
                "IT", "Literature", "Math", "Physics", "Politics",
                "Sports", "Social studies", "Other"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                NewProjectActivity.this,
                R.layout.dropdown_menu_categories,
                items
        );

        final boolean[] private_piblic_project = {false};

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        switchPrivate_proj.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                private_piblic_project[0] = true;
                Log.i(TAG, "Переменная (true): " + private_piblic_project[0]);
                editor.putBoolean("private_public_mail_proj", private_piblic_project[0]);
            } else {
                private_piblic_project[0] = false;
                Log.i(TAG, "Переменная (false): " + private_piblic_project[0]);
                editor.putBoolean("private_public_mail_proj", private_piblic_project[0]);
            }

        });

        autoCompleteTextView.setAdapter(adapter);

        Button next = findViewById(R.id.next_btn);
        next.setOnClickListener(v -> {

            if(!vaildateNameProject() | !validateDescriptionProject() | !validateTopicProject()) {
                return;
            } else {

                String nameProject = Objects.requireNonNull(textField_name_proj.getEditText()).getText().toString();
                String description = Objects.requireNonNull(textField_description_proj.getEditText()).getText().toString();
                String topic = Objects.requireNonNull(textField_topic.getEditText()).getText().toString();

                Log.i(TAG, "name = " + nameProject);
                Log.i(TAG, "description = " + description);
                Log.i(TAG, "topic = " + topic);

                editor.putString("nameMainProject", nameProject);
                editor.putString("descriptionMainProject", description);
                editor.putString("topicMainProject", topic);

                editor.apply();

                startActivity(new Intent(NewProjectActivity.this, NewProjectImagesActivity.class));

            }

        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public boolean vaildateNameProject() {
        String check = Objects.requireNonNull(textField_name_proj.getEditText()).getText().toString().trim();

        if(check.isEmpty()){
            textField_name_proj.setError("Это поле не может быть пустом");

            return false;
        } else {
            textField_name_proj.setError(null);
            return true;
        }

    }

    public boolean validateDescriptionProject() {
        String check = Objects.requireNonNull(textField_description_proj.getEditText()).getText().toString().trim();

        if(check.isEmpty()){
            textField_description_proj.setError("Это поле не может быть пустом");

            return false;
        } else {
            textField_description_proj.setError(null);
            return true;
        }
    }

    public boolean validateTopicProject() {
        String check = Objects.requireNonNull(textField_topic.getEditText()).getText().toString().trim();

        if(check.isEmpty()){
            textField_topic.setError("Это поле не может быть пустом");

            return false;
        } else {
            textField_topic.setError(null);
            return true;
        }
    }


}