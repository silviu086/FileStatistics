package com.example.silviu.filesstat;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        File storage;
        final File[] listFiles;
        ArrayList<Long> listSize;
        ArrayList<Integer> listDirectoryFiles;


        storage = new File(Environment.getExternalStorageDirectory().getAbsoluteFile().toString());
        listFiles = storage.listFiles();
        listSize = new ArrayList<>();
        listDirectoryFiles = new ArrayList<>();
        for(File file : storage.listFiles()){
            if(file.isDirectory()){
                listDirectoryFiles.add(Functions.getDirectoryFileCount(file));
            }else{
                listDirectoryFiles.add(-1);
            }
            long size = Functions.getDirectorySize(file);
            listSize.add(size);
        }

        FileAdapter adapter = new FileAdapter(getApplicationContext(), storage, listSize, listDirectoryFiles);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ScrollingActivity.this, listFiles[position].getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }
}
