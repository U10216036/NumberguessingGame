package com.example.owner.numberguessinggame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView itemsListView  = (ListView)findViewById(R.id.listView);
        Listviewadapter adapter = new Listviewadapter(this, generateItemsList());
        itemsListView.setAdapter(adapter);
    }

    /**
     * Util function to generate list of items
     *
     * @return ArrayList
     */
    private ArrayList<Answer> generateItemsList() {
        String itemNames[] = {"XD"};


        ArrayList<Answer> list = new ArrayList<>();

        for (int i = 0; i < itemNames.length; i++) {
            list.add(new Answer(itemNames[i]));
        }

        return list;
    }
}
