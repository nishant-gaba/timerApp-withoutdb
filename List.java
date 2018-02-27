package com.example.nishant.timer;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.sql.Time;
import java.util.ArrayList;

public class List extends AppCompatActivity {

    ListView list ;
    ArrayList<String> data;
    Adapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

      list=(ListView)findViewById(R.id.listview);
      timerlist();

    }
    void timerlist() {

        data = new ArrayList<String>();

        try {

            SQLiteDatabase db = this.openOrCreateDatabase("timing", MODE_PRIVATE, null);
            Cursor c = db.rawQuery("Select * from data", null);
            c.moveToFirst();

            int time = c.getColumnIndex("TIME");
            int date = c.getColumnIndex("DATE");
            int name = c.getColumnIndex("NAME");

            while (c != null) {

                data.add("\n\n" + c.getString(date) + "  " + c.getString(name) + "  " + c.getString(time));
                c.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

          adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
          list.setAdapter((ListAdapter) adapter);
        }
    }
}