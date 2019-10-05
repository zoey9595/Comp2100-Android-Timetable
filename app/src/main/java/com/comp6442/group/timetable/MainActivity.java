package com.comp6442.group.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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