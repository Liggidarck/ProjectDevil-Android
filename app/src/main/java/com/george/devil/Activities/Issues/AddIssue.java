package com.george.devil.Activities.Issues;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.george.devil.DataBases.IssuesDataBase;
import com.george.devil.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddIssue extends AppCompatActivity {

    //TODO: ПЕРЕДЕЛАТЬ ЭТОТ ПИЗДЕЦ В БОЛЕЕ ЧИТАЕМЫЙ С КОММЕНТАРИЯМИ

    EditText name_issue, editText_add_due_date;

    IssuesDataBase sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long issuesId = 0;

    RelativeLayout choose_priority_lay;

    String dateFullIssue, noteissue;
    TextView dateViewIssue, text_note_issue;

    private static final String TAG = "AddIssueActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue);

        choose_priority_lay = findViewById(R.id.choose_priority_lay);

        MaterialToolbar topAppBar_add_issue = findViewById(R.id.topAppBar_add_issue);
        topAppBar_add_issue.setNavigationOnClickListener(v -> save());

        ImageView delete_isseue = findViewById(R.id.delete_issue);
        delete_isseue.setOnClickListener(v -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            boolean confirmDelet = preferences.getBoolean("delet_bool", true);

            if(confirmDelet){
                AlertDialog.Builder builder = new AlertDialog.Builder(AddIssue.this);
                builder.setTitle(getText(R.string.attention));
                builder.setMessage(getText(R.string.confirm_delete_issue));

                builder.setPositiveButton(getString(android.R.string.ok), (dialog, id) -> {
                    delete();
                    dialog.dismiss();
                });

                builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.dismiss());

                builder.show();
            }else
                delete();


        });

        name_issue = findViewById(R.id.name_issue);
        editText_add_due_date = findViewById(R.id.editText_add_due_date);
        dateViewIssue = findViewById(R.id.date_issue);
        text_note_issue = findViewById(R.id.text_note_issue);

        sqlHelper = new IssuesDataBase(this);
        db = sqlHelper.getWritableDatabase();

        choose_priority_lay.setOnClickListener(v -> onpenDialog());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            issuesId = extras.getLong("id");
            noteissue = extras.getString("noteIssue");
        }

        Log.i(TAG, "Полученое значение заметки - " + noteissue);
        text_note_issue.setText(noteissue);

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);
        dateFullIssue = getText(R.string.last_modified) + ":" + " " + dateText + " " + timeText;
        dateViewIssue.setText(dateFullIssue);

        if (issuesId > 0) {
            userCursor = db.rawQuery("select * from " + IssuesDataBase.TABLE + " where " + IssuesDataBase.COLUMN_ID + "=?", new String[]{String.valueOf(issuesId)});
            userCursor.moveToFirst();

            name_issue.setText(userCursor.getString(1));
            editText_add_due_date.setText(userCursor.getString(3));
            dateViewIssue.setText(userCursor.getString(4));
            text_note_issue.setText(userCursor.getString(5));

            userCursor.close();
        }

        MaterialCardView addNote = findViewById(R.id.add_note_issue_layout);
        addNote.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddNoteIssue.class);
            intent.putExtra("name_issue", name_issue.getText().toString());
            startActivity(intent);
        });


    }

    public void save() {
        String chekName = name_issue.getText().toString();

        if (!chekName.isEmpty()) {
            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String dateText = dateFormat.format(currentDate);
            DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String timeText = timeFormat.format(currentDate);
            String fullDate = getText(R.string.last_modified) + ":" + " " + dateText + " " + timeText;

            ContentValues cv = new ContentValues();
            cv.put(IssuesDataBase.COLUMN_NAME_ISSUE, name_issue.getText().toString());
            cv.put(IssuesDataBase.COLUMN_DATE, editText_add_due_date.getText().toString());
            cv.put(IssuesDataBase.COLUMN_DATE_SAVE, fullDate);

            if (issuesId > 0)
                db.update(IssuesDataBase.TABLE, cv, IssuesDataBase.COLUMN_ID + "=" + issuesId, null);
            else
                db.insert(IssuesDataBase.TABLE, null, cv);

        }
        goHome();
    }

    public void goHome() {
        db.close();
        Intent intent = new Intent(this, IssuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void delete() {
        db.delete(IssuesDataBase.TABLE, "_id = ?", new String[]{String.valueOf(issuesId)});
        goHome();
    }

    public void onpenDialog() {
        Dialog dialog = new Dialog(AddIssue.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_proirity);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        save();
    }
}