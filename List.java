package com.example.nishant.timer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;

import java.sql.Time;
import java.util.ArrayList;

public class List extends AppCompatActivity {

    EditText d ;
    SeekBar seek ;

    ListView list = (ListView)findViewById(R.id.listview);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



    }
}