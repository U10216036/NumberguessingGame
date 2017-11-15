package com.example.owner.numberguessinggame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int a[];
    int notguess[];
    int ans[] = new int[2];
    int guess[] = new int[2];
    ListView itemsListView;
    TextView textAns;
    ArrayList<Answer> list;
    Listviewadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.btn);
        textAns = (TextView)findViewById(R.id.textAns);
        itemsListView  = (ListView)findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new Listviewadapter(this, list);
        itemsListView.setAdapter(adapter);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                a = new int[5];
                for(int i =0;i < a.length;i++){
                    a[i] = i+1;
                }
                generateRandomAns();

                for(int i =0;i < a.length;i++){
                    list.add(new Answer(String.valueOf(a[i])));
                }

                adapter.notifyDataSetChanged();
            }
        });
    }

    void generateRandomAns(){
        textAns.setText("");
        ans[0] = (int)(Math.random()*(a.length)+1);
        ans[1] =(int)(Math.random()*(a.length)+1);
        while (ans[1] == ans[0]){
            ans[1] =(int)(Math.random()*(a.length)+1);
        }

        for (int k=0;k<ans.length;k++){
            textAns.append(String.valueOf(ans[k] + " "));
        }
    }

    String StartGuessing(int[] a,int length){
        notguess = new int[length];
        guess[0] = notguess[(int)(Math.random()*(length))];
        guess[1] =notguess[(int)(Math.random()*(length))];
        while (ans[1] == ans[0]){
            ans[1] =notguess[(int)(Math.random()*(length))];
        }
        if (guess[0]!=ans[0]&&guess[1]!=ans[1]){

            return StartGuessing(a,length-2);
        }else {
            return "我猜是:"+String.valueOf(guess[0])+" "+String.valueOf(guess[1])+"";
        }

    }


}
