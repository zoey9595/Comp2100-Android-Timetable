package com.comp6442.group.timetable;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimeTableLayout timeTable = findViewById(R.id.timeTable);
        timeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> lesson = (HashMap<String, String>)view.getTag();
                showInfoDialog(lesson.get("name"), lesson);
            }
        });
    }

    private void showInfoDialog(String courseName, final Map<String, String> lesson) {
        String message = String.format(
                Locale.ENGLISH, "%s%s\n%s%s\n%s%s",
                "Start Time: ", lesson.get("start"),
                "  End Time: ", lesson.get("end"),
                "   Location: ", lesson.get("location"));
        AlertDialog.Builder courseDialogBuilder = new AlertDialog.Builder(this)
                .setTitle(courseName)
                .setMessage(message)
                .setPositiveButton("Detail", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(lesson.get("description")));
                        startActivity(browserIntent);
                    }
                });
        courseDialogBuilder.show();
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();
        if (id == R.id.btn_add) {
            // Start EnrolActivity
            intent = new Intent(MainActivity.this, EnrolActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_question) {
            // Start GuideActivity
            intent = new Intent(MainActivity.this, GuideActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}