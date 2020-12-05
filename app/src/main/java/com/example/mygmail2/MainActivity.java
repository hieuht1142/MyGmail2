package com.example.mygmail2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.bloco.faker.Faker;
import io.bloco.faker.components.Time;

public class MainActivity extends AppCompatActivity implements EmailAdapter.ItemClickListener {

    List<MyModel> allmails;
    List<MyModel> starMails;
    EmailAdapter adapter;
    EmailAdapter starAdapter;
    ListView listView;
    SearchView searchView;
    int option = 0; // 0: all emails, 1: star emails

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        actionBar.setTitle("My Email");

        actionBar.setDisplayShowCustomEnabled(true); // allow custom views to be shown
        actionBar.setDisplayHomeAsUpEnabled(false); // show ‘UP’ affordance < button
        actionBar.setDisplayShowHomeEnabled(false); // allow app icon – logo to be shown
        actionBar.setHomeButtonEnabled(false); // needed for API14 or greater

        listView = findViewById(R.id.list_view);

        starMails = new ArrayList<>();
        initMails();

        adapter = new EmailAdapter(this, allmails, this);
        starAdapter = new EmailAdapter(this, starMails, this);

        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        listView.setLongClickable(true);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        int i = info.position;

        if (id == R.id.action_delete) {
            if (option == 0) {
                starMails.remove(allmails.get(i));
                allmails.remove(i);
            } else {
                allmails.remove(starMails.get(i));
                starMails.remove(i);
            }
            adapter.notifyDataSetChanged();
            starAdapter.notifyDataSetChanged();

        } else if (id == R.id.action_reply) {
            Intent intent = new Intent(MainActivity.this, ReplyMailActivity.class);
            intent.putExtra("username", allmails.get(i).getUsername());
            intent.putExtra("title", allmails.get(i).getTitle());
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            EmailAdapter sAdapter;

            @Override
            public boolean onQueryTextSubmit(String query) {
                listView.setAdapter(sAdapter);

                invalidateOptionsMenu();
                //searchView.setQuery("", false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                EmailAdapter sAdapter = new EmailAdapter(MainActivity.this, filter(newText), MainActivity.this);
                listView.setAdapter(sAdapter);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_show_star) {
            listView.setAdapter(starAdapter);
            option = 1;
        } else if (id == R.id.action_show_all) {
            listView.setAdapter(adapter);
            option = 0;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMails() {
        allmails = new ArrayList<>();
        Faker faker = new Faker();

        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Time time = faker.time;

        allmails.add(new MyModel(faker.name.name(), faker.lorem.sentence(1),
                faker.lorem.sentence(1), "12:00 AM"));
        allmails.add(new MyModel(faker.name.name(), faker.lorem.sentence(1),
                faker.lorem.sentence(1), "12:00 AM"));
        allmails.add(new MyModel(faker.name.name(), faker.lorem.sentence(1),
                faker.lorem.sentence(1), "12:00 AM"));
        allmails.add(new MyModel(faker.name.name(), faker.lorem.sentence(1),
                faker.lorem.sentence(1), "12:00 AM"));
        allmails.add(new MyModel(faker.name.name(), faker.lorem.sentence(1),
                faker.lorem.sentence(1), "12:00 AM"));
        allmails.add(new MyModel(faker.name.name(), faker.lorem.sentence(1),
                faker.lorem.sentence(1), "12:00 AM"));
        allmails.add(new MyModel(faker.name.name(), faker.lorem.sentence(1),
                faker.lorem.sentence(1), "12:00 AM"));
        allmails.add(new MyModel(faker.name.name(), faker.lorem.sentence(1),
                faker.lorem.sentence(1), "12:00 AM"));
        allmails.add(new MyModel(faker.name.name(), faker.lorem.sentence(1),
                faker.lorem.sentence(1), "12:00 AM"));
        allmails.add(new MyModel(faker.name.name(), faker.lorem.sentence(1),
                faker.lorem.sentence(1), "12:00 AM"));
        allmails.add(new MyModel(faker.name.name(), faker.lorem.sentence(1),
                faker.lorem.sentence(1), "12:00 AM"));
        allmails.add(new MyModel(faker.name.name(), faker.lorem.sentence(1),
                faker.lorem.sentence(1), "12:00 AM"));
    }

    @Override
    public void processImageStarClick(int position) {
        if (option == 0) {
            if (allmails.get(position).getImageStar() == R.drawable.ic_star_border) {
                allmails.get(position).setImageStar(R.drawable.ic_star);
                starMails.add(allmails.get(position));
            } else {
                allmails.get(position).setImageStar(R.drawable.ic_star_border);
                starMails.remove(allmails.get(position));
            }
            adapter.notifyDataSetChanged();
        } else {

        }
    }

    private List<MyModel> filter(String keyWord) {
        keyWord = keyWord.toLowerCase(Locale.getDefault());
        List<MyModel> result = new ArrayList<>();
        if (keyWord.equals("")) {
            result.addAll(allmails);
        } else {
            for (MyModel m: allmails) {
                if (m.getUsername().toLowerCase(Locale.getDefault()).contains(keyWord)) {
                    result.add(m);
                } else if (m.getTitle().toLowerCase(Locale.getDefault()).contains(keyWord)) {
                    result.add(m);
                }
            }
        }
        return result;
    }
}