package com.example.akshi.attendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    EditText clubName,date,time,event;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        clubName = findViewById(R.id.clubName);
        time = findViewById(R.id.timeOfEvent);
        date = findViewById(R.id.dateOfEvent);
        event = findViewById(R.id.eventName);
        done = findViewById(R.id.done);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(clubName.getText())||TextUtils.isEmpty(date.getText())||TextUtils.isEmpty(time.getText())||
                        TextUtils.isEmpty(event.getText())) {
                    Toast.makeText(Details.this,"Fill details",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(Details.this,Publish.class);
                    String det = clubName.getText().toString()+","+event.getText().toString()+","
                            +date.getText().toString()+","+time.getText().toString();
                    intent.putExtra("Details",det);
                    startActivity(intent);
                }
            }
        });
    }
}
