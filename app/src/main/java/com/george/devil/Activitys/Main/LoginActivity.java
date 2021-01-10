package com.george.devil.Activitys.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.george.devil.Activitys.Main.Pupil.MainActivityPupil;
import com.george.devil.Activitys.Main.Registration.RegistarionPupilActivity;
import com.george.devil.Activitys.Main.Registration.RegistrationTeatherActivity;
import com.george.devil.Activitys.Main.Teather.MainActivityTeather;
import com.george.devil.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout email_login_text_layout, password_login_text_layout;
    static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        TextView reg = findViewById(R.id.registr);
        email_login_text_layout = findViewById(R.id.email_login_text_layout);
        password_login_text_layout = findViewById(R.id.password_login_text_layout);
        ImageView login_btn = findViewById(R.id.sign_in_btn_1);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String name_user_pupil = sharedPreferences.getString("full_name", "empty_user_name");
        String username_pupil = sharedPreferences.getString("username", "empty_correct_username");
        String topic_pupil = sharedPreferences.getString("topik", "empty_topic");
        String email_pupil = sharedPreferences.getString("email", "empty_email");
        String city_pupil = sharedPreferences.getString("city", "empty_city");
        String school_pupil = sharedPreferences.getString("school", "empty_school");
        String grade_pupil = sharedPreferences.getString("grade", "empty_grade");
        String birthday_pupil = sharedPreferences.getString("birthday", "empty_birthday");
        String password_pupil = sharedPreferences.getString("pas", "empty_pas");

        Log.e(TAG, "Pupil data");
        Log.e(TAG, "Name pupil - " + name_user_pupil);
        Log.e(TAG, "username pupil - " + username_pupil);
        Log.e(TAG, "topic pupil - " + topic_pupil);
        Log.e(TAG, "email pupil - " + email_pupil);
        Log.e(TAG, "city pupil - " + city_pupil);
        Log.e(TAG, "school pupil - " + school_pupil);
        Log.e(TAG, "grade pupil - " + grade_pupil);
        Log.e(TAG, "birthday pupil" + birthday_pupil);
        Log.e(TAG, "password pupil - " + password_pupil);
        Log.e(TAG, "---------------");

        String name_teather = sharedPreferences.getString("full_name_teather", "full_name_teather_empty");
        String username_teather = sharedPreferences.getString("username_teather", "username_teather_empty");
        String email_teather = sharedPreferences.getString("email_teather", "email_teather_empty");
        String city_teather = sharedPreferences.getString("city_teather", "city_teather_empty");
        String topic_teather = sharedPreferences.getString("topic_teather", "topic_teather_empty");
        String school_teather = sharedPreferences.getString("school_teather","school_teather_empty");
        String birthay_teather = sharedPreferences.getString("birthay_teather","birthay_teather_empty");
        String password_teather = sharedPreferences.getString("password_teather","password_teather_empty");

        Log.e(TAG, "Teather data");
        Log.e(TAG, "name teather - " + name_teather);
        Log.e(TAG, "username teather - " + username_teather);
        Log.e(TAG, "email teather - " + email_teather);
        Log.e(TAG, "city teathret - " + city_teather);
        Log.e(TAG, "topic teather - " + topic_teather);
        Log.e(TAG, "school teather - " + school_teather);
        Log.e(TAG, "birtghay teather - " + birthay_teather);
        Log.e(TAG, "password teather - " + password_teather);

        if(!name_user_pupil.equals("empty_user_name")) {
            Log.i(TAG, "Имя ученика НЕПУСТОЕ");
            Log.i(TAG, "Запуск PupilActiivty");
            startActivity(new Intent(LoginActivity.this, MainActivityPupil.class));
        }

        if(!name_teather.equals("full_name_teather_empty")) {
            Log.i(TAG, "Имя учителя НЕПУСТОЕ");
            startActivity(new Intent(LoginActivity.this, MainActivityTeather.class));
        }

        reg.setOnClickListener(v -> showResistrDialog());

        login_btn.setOnClickListener(v -> {
            String email = Objects.requireNonNull(email_login_text_layout.getEditText()).getText().toString();
            String password = Objects.requireNonNull(password_login_text_layout.getEditText()).getText().toString();
            if(email.equals("liggidarck@gmail.com") & password.equals("qwerty")){

                editor.putString("full_name", "George Filatov");
                editor.putString("username", "liggidarck");
                editor.putString("topik", "IT");
                editor.putString("email", email);
                editor.putString("city", "Moscow");
                editor.putString("school", "2122");
                editor.putString("grade", "10A");
                editor.putString("birthday", "01.06.2004");
                editor.putString("pas", password);

                editor.apply();

                startActivity(new Intent(LoginActivity.this, MainActivityPupil.class));
            }

        });

    }

    private boolean validateEmail() {
        String email = Objects.requireNonNull(email_login_text_layout.getEditText()).getText().toString();

        if(email.isEmpty()){
            email_login_text_layout.setError("Ошибка! Поле не может быть пустым");
            return false;
        } else {
            email_login_text_layout.setError(null);
            return true;
        }
    }

    private boolean validatePssword() {
        String email = Objects.requireNonNull(email_login_text_layout.getEditText()).getText().toString();

        if(email.isEmpty()){
            email_login_text_layout.setError("Ошибка! Поле не может быть пустым");
            return false;
        } else {
            email_login_text_layout.setError(null);
            return true;
        }

    }

    public void showResistrDialog() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_choose_category_login);

        Button next = dialog.findViewById(R.id.cancel_dialog_btn);
        next.setOnClickListener(v -> dialog.dismiss());

        RelativeLayout pupil_ralative_layout = dialog.findViewById(R.id.pupil_ralative_layout);
        pupil_ralative_layout.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegistarionPupilActivity.class)));

        RelativeLayout teacher_relative_layout = dialog.findViewById(R.id.teacher_relative_dilaog);
        teacher_relative_layout.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegistrationTeatherActivity.class)));

        dialog.show();
    }

}