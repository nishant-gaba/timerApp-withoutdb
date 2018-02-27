package com.example.nishant.timer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes;

public class timer extends AppCompatActivity {

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }
    SeekBar seek ;

    EditText textview,name ;

    int progressChangedValue;
    String Date,timing ;
    CountDownTimer timer ;

    MediaPlayer ring ;
    Calendar calendar ;
    SimpleDateFormat simpleDateFormat ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        final Button start = (Button)findViewById(R.id.button);
        final Button pause = (Button)findViewById(R.id.button2);

        pause.setEnabled(false);

        seek = (SeekBar)findViewById(R.id.seekBar);

        textview=(EditText)findViewById(R.id.editText8);
        name = (EditText)findViewById(R.id.editText);
        ring = MediaPlayer.create(timer.this,R.raw.ring);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress ;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                  @SuppressLint("DefaultLocale")  String hms1 =String.format("%02d : %02d : %02d ",
                          TimeUnit.MILLISECONDS.toHours(progressChangedValue*1000),
                          TimeUnit.MILLISECONDS.toMinutes(progressChangedValue*1000)-
                          TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(progressChangedValue*1000)),
                          TimeUnit.MILLISECONDS.toSeconds(progressChangedValue*1000)-
                                  TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(progressChangedValue*1000)));
                   timing=hms1;
                  textview.setText(hms1);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View view) {

                calendar= Calendar.getInstance();
                simpleDateFormat =new SimpleDateFormat("dd-MM-yyyy hh : mm a");
                Date = simpleDateFormat.format(calendar.getTime());


                if (progressChangedValue != 0) {
                    start.setEnabled(false);
                    pause.setEnabled(true);
                    timer = new CountDownTimer(progressChangedValue * 1000, 1000) {
                        @Override
                        public void onTick(long l) {

                            @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d",
                                    TimeUnit.MILLISECONDS.toHours(l),
                                    TimeUnit.MILLISECONDS.toMinutes(l) -
                                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),
                                    TimeUnit.MILLISECONDS.toSeconds(l) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                               textview.setText(hms);
                        }

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onFinish() {
                            seek.setProgress(0);
                            textview.setText("00:00:00");
                            start.setEnabled(true);
                            pause.setEnabled(false);
                            notes(Date,name.getText(),timing);
                            toastMsg("finished");
                           ring.start();
                        }
                    }.start();
                }
                    else
                {
                    if(progressChangedValue==0){
                        toastMsg("SET TIMER FIRST");
                    }

                }
                pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timer.cancel();
                        pause.setEnabled(false);
                        start.setEnabled(true);
                        seek.setProgress(0);

                        textview.setText("00:00:00");
                    }
        });
                }

        });

      //  notes();
    }

    void notes(String date, Editable name, String time ){
        try{

            SQLiteDatabase db = this.openOrCreateDatabase("timing",MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS data(DATE VARCHAR,NAME VARCHAR,TIME VARCHAR)");
            db.execSQL("INSERT INTO data(DATE,NAME,TIME) VALUES('"+date+"','"+name+"','"+time+"')");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

