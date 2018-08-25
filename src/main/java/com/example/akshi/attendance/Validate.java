package com.example.akshi.attendance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Validate extends AppCompatActivity {

    ListView listView;
    Button validate;
    List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);
        listView = findViewById(R.id.listCheck);
        validate = findViewById(R.id.validate);
        list = new ArrayList<>();
        Intent i= getIntent();
        String name = i.getStringExtra("Name");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("events").child(name);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                    list.add(ds.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setAdapter(new ArrayAdapter<>(Validate.this,android.R.layout.simple_list_item_1,list));
    }
}
