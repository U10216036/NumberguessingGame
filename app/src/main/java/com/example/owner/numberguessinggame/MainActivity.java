package com.example.owner.numberguessinggame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.btn);
        ListView itemsListView  = (ListView)findViewById(R.id.listView);
        ArrayList<Answer> list = new ArrayList<>();

        Listviewadapter adapter = new Listviewadapter(this, list);
        itemsListView.setAdapter(adapter);
        list.add(new Answer("XD"));
        list.add(new Answer("22"));
    }


}
