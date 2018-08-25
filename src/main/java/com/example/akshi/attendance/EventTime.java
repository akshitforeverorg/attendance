package com.example.akshi.attendance;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EventTime extends AppCompatActivity {

    Button timePick,publish;
    Calendar calendar = Calendar.getInstance();
    boolean isDateSet,is_Time_Set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_time);
        timePick = findViewById(R.id.timePick);
        publish = findViewById(R.id.publish);
        Intent intent = getIntent();

        String rollList = intent.getStringExtra("list");
        Toast.makeText(this,rollList,Toast.LENGTH_LONG).show();
        String rolls[] = rollList.split("~");
        final String details[] = rolls[0].split(",");
        final String rollsNo[] = rolls[1].split(",");

        final String dateTime[] = new String[1];
        timePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener dates = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int j1, int j2) {
                        calendar.set(Calendar.HOUR_OF_DAY,j1);
                        calendar.set(Calendar.MINUTE,j2);
                        is_Time_Set=true;
                    }
                };
                final TimePickerDialog timePickerDialog = new TimePickerDialog(EventTime.this, dates, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                DatePickerDialog.OnDateSetListener dater = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DATE, i2);
                        isDateSet = true;
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(EventTime.this, dater, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Toast.makeText(getApplicationContext(), "Enter date", Toast.LENGTH_LONG).show();
                        timePickerDialog.show();
                    }
                });
                timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if(is_Time_Set&&isDateSet) {
                            dateTime[0] = calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR);
                            if(calendar.get(Calendar.HOUR_OF_DAY)<10) dateTime[0]+="/0"+calendar.get(Calendar.HOUR_OF_DAY);
                            else dateTime[0]+="/"+calendar.get(Calendar.HOUR_OF_DAY);
                            if(calendar.get(Calendar.MINUTE)<10) dateTime[0]+="/0"+calendar.get(Calendar.MINUTE);
                            else dateTime[0]+="/"+calendar.get(Calendar.MINUTE);
                        }
                    }
                });
                datePickerDialog.show();
    }});
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateTime[0]==null) Toast.makeText(EventTime.this,"Please select a date",Toast.LENGTH_LONG).show();
                else{
                    Toast.makeText(EventTime.this,dateTime[0],Toast.LENGTH_LONG).show();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("events").
                            child(details[0]).child(details[1]);
                    databaseReference.child("Date").setValue(details[2]);
                    databaseReference.child("Time").setValue(details[3]);
                    for (String roll : rollsNo) databaseReference.push().setValue(roll);
                }
            }
        });
}
}
