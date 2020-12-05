package com.example.mygmail2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ReplyMailActivity extends AppCompatActivity {

    EditText edtTo;
    EditText edtSubject;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_mail);

        actionBar = getSupportActionBar();

        actionBar.setTitle("Reply");

        actionBar.setDisplayShowCustomEnabled(true); // allow custom views to be shown
        actionBar.setDisplayHomeAsUpEnabled(true); // show ‘UP’ affordance < button
        actionBar.setDisplayShowHomeEnabled(true); // allow app icon – logo to be shown
        actionBar.setHomeButtonEnabled(true); // needed for API14 or greater

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String title = intent.getStringExtra("title");

        edtTo = findViewById(R.id.edt_to);
        edtSubject = findViewById(R.id.edt_subject);

        edtTo.setText(username);
        edtSubject.setText("Re: " + title);

        setResult(Activity.RESULT_OK, intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reply, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_send) {
            Toast.makeText(ReplyMailActivity.this, "Send successfully", Toast.LENGTH_LONG).show();
            finish();
        } else if (id == R.id.action_cancel) {
            finish();
        } else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}