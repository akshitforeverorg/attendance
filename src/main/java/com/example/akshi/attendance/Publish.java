package com.example.akshi.attendance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Publish extends AppCompatActivity {
    ListView listView,addedLV;
    List<String> list;
    List<String> added;
    Button viewAdded,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish2);

        listView=findViewById(R.id.list);
        viewAdded =findViewById(R.id.viewAdded);
        addedLV = findViewById(R.id.listAdded);
        next = findViewById(R.id.next);

        Intent inte = getIntent();
        final String details = inte.getStringExtra("Details");

        list = new ArrayList<>(310);
        added = new ArrayList<>();
        for(int i=1627944;i<1628260;i++) list.add(Integer.toString(i));
        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                added.add(list.remove(position));
                listView.setAdapter(new ArrayAdapter<>(Publish.this,android.R.layout.simple_list_item_1,list));
            }
        });
        viewAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(viewAdded.getText().toString().equals("Added")) {
                    addedLV.setAdapter(new ArrayAdapter<>(Publish.this, android.R.layout.simple_list_item_1, added));
                    listView.setVisibility(View.GONE);
                    addedLV.setVisibility(View.VISIBLE);
                    viewAdded.setText("Normal");
                }
                else{
                    listView.setAdapter(new ArrayAdapter<>(Publish.this, android.R.layout.simple_list_item_1, list));
                    addedLV.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    viewAdded.setText("Added");
                }

            }
        });
        addedLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.add(position,added.remove(position));
                addedLV.setAdapter(new ArrayAdapter<>(Publish.this,android.R.layout.simple_list_item_1,added));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Publish.this,EventTime.class);
                if(!added.isEmpty()) {
                    String add = added.toString().replace("[","");
                    add = add.replace("]","");
                    add = details +"~"+add;
                    intent.putExtra("list", add);
                    startActivity(intent);
                }
                else Toast.makeText(Publish.this,"Please select some students",Toast.LENGTH_LONG).show();

            }
        });
    }
}
