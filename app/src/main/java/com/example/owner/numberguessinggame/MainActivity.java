package com.example.owner.numberguessinggame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    List<Integer> a = new ArrayList<>();
    List<Integer> notguess = new ArrayList<>();
    List<Integer> removedAns = new ArrayList<>();
    int ans[] = new int[2];
    int guess[] = new int[2];
    ListView itemsListView;
    TextView textAns,value;
    Button btn;
    SeekBar seekBar;
    ArrayList<Answer> list;
    Listviewadapter adapter;
    int guessIndex;
    int count1C =0; //count 1C
    boolean count2C =false; //2C or not when start guessing
    Random ran;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.btn);
        textAns = (TextView)findViewById(R.id.textAns);
        itemsListView  = (ListView)findViewById(R.id.listView);
        seekBar = (SeekBar) findViewById(R.id.seek);
        seekBar.setProgress(3);
        value = (TextView)findViewById(R.id.value);
        ran = new Random();
        list = new ArrayList<>();
        adapter = new Listviewadapter(this, list);

        itemsListView.setAdapter(adapter);
        //set how may number to guess
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value.setText( String.valueOf(i));
                if(i ==0){
                    value.setText(String.valueOf(3));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //click star button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearAnswer();
                count1C = 0;
                count2C = false;
                removedAns.clear();
                notguess.clear();
                //add 1-N numbers to a
                int number = Integer.valueOf((String) value.getText());
                for(int i =0;i <number;i++){
                    a.add(i+1);
                }
                //generate answer by random
                generateRandomAns();
                //Start guessing
                String result = StartGuessing(a);
                //Add result to list
                list.add(new Answer(result));
                //show result on listview
                adapter.notifyDataSetChanged();
                notguess.clear();

            }
        });
    }


    void generateRandomAns(){
        textAns.setText("");
        ans[0] = a.get(ran.nextInt(a.size()));
        ans[1] = a.get(ran.nextInt(a.size()));
        //prevent generate same number
        if (ans[1] == ans[0]){
            Iterator<Integer> Iterator = a.iterator();
            while(Iterator.hasNext()){
                int e = Iterator.next();
                if(e == ans[0]){
                    Iterator.remove();
                }
            }
            if(a.size()!=0) {
                ans[1] = a.get(ran.nextInt(a.size()));
                a.add(ans[0]);
            }
        }
        if(ans[0]>ans[1]){
            int temp = ans[0];
            ans[0] = ans[1];
            ans[1] = temp;
        }


        textAns.setText(ans[0] + " " + ans[1]);
        Log.i("StartGuessing: ","ans:"+String.valueOf(ans[0])+" "+String.valueOf(ans[1]));
    }
    //Start guessing number
    String StartGuessing(List a){
        Log.i("notguess:",String.valueOf(notguess));
        notguess = a;
        //if no 1C occurs just guess two random numbers from notguess
        if (count1C == 0 ){
            guess[0] = notguess.get(ran.nextInt(notguess.size()));
            guess[1] = notguess.get(ran.nextInt(notguess.size()));
            //prevent guessed two same numbers
            if (guess[1] == guess[0]) {
                Iterator<Integer> Iterator = notguess.iterator();
                while (Iterator.hasNext()) {
                    int e = Iterator.next();
                    if (e == guess[0]) {
                        Iterator.remove();
                    }
                }
                if (notguess.size() != 0) {
                    guess[1] = notguess.get(ran.nextInt(notguess.size()));
                    notguess.add(guess[0]);
                }
            }
            count2C = false;
        }
        //if 1C occured then 2C occcured  it means the second time you chose the wrong correct number.
        //So get back of the other number at fist time you chose
        else if (count1C==1 && count2C == true){
            // get back of the other number at fist time you chose
            guess[0] = removedAns.get(0);
            // add it to notguess
            notguess.add(removedAns.get(0));
            // remove it from removedAns
            Iterator<Integer> Iterator = removedAns.iterator();
            while (Iterator.hasNext()) {
                int e = Iterator.next();
                if (e == removedAns.get(0)) {
                    Iterator.remove();
                }
            }
            //random guess the sencond number from notguess
            guess[1] = notguess.get(ran.nextInt(notguess.size()));
            //prevent guessed two same numbers
            if (guess[1] == guess[0]) {
                Iterator<Integer> Iterator2 = notguess.iterator();
                while (Iterator2.hasNext()) {
                    int e = Iterator2.next();
                    if (e == guess[0]) {
                        Iterator2.remove();
                    }
                }
                if (notguess.size() != 0) {
                    guess[1] = notguess.get(ran.nextInt(notguess.size()));
                    notguess.add(guess[0]);
                }
            }
            count2C = false;
        }
        //if 1C occured choose one of the guessed number be correct number
        else {
            if (notguess.size()>1){
                guess[1-guessIndex] = notguess.get(ran.nextInt(notguess.size()));
                if (guess[1] == guess[0]) {
                    Iterator<Integer> Iterator = notguess.iterator();
                    while (Iterator.hasNext()) {
                        int e = Iterator.next();
                        if (e == guess[0]) {
                            Iterator.remove();
                        }
                    }
                    if (notguess.size() != 0) {
                        guess[1] = notguess.get(ran.nextInt(notguess.size()));
                        notguess.add(guess[0]);
                    }
                }
            }//if notguess only have one number , it means the correct answer is in removedAns
            else {
                guess[0] = removedAns.get(0);
                guess[1] = removedAns.get(1);
            }

        }


        //if 2C, remove the two numbers from notguess
        if ((guess[0]!=ans[0]&&guess[1]!=ans[1])&&(guess[1]!=ans[0]&&guess[0]!=ans[1])){

            count2C = true;
            list.add(new Answer("我猜是"+String.valueOf(guess[0])+" "+String.valueOf(guess[1])+"全錯0C QAQ"));
            Log.i("StartGuessing: ","我猜是"+String.valueOf(guess[0])+" "+String.valueOf(guess[1])+"全錯0C QAQ");
            Iterator<Integer> Iterator = notguess.iterator();
            while(Iterator.hasNext()){
                int e = Iterator.next();
                if(e == guess[0]||e == guess[1]){
                    Iterator.remove();
                }
            }
            Log.i("removedans:",String.valueOf(removedAns));
            //return notguess
            return StartGuessing(notguess);
        }
        //if 1C,decide one of the number is wrong then removed it from notguess and add to removedAns
        else if ((guess[0]==ans[0]&&guess[1]!=ans[1])||(guess[0]!=ans[0]&&guess[1]==ans[1])||(guess[1]!=ans[0]&&guess[0]==ans[1])||(guess[1]==ans[0]&&guess[0]!=ans[1])){

            count1C++;
            list.add(new Answer("我猜是"+String.valueOf(guess[0])+" "+String.valueOf(guess[1])+"對一個1C >_<"));
            Log.i("StartGuessing: ","我猜是"+String.valueOf(guess[0])+" "+String.valueOf(guess[1])+"對一個1C >_<");
            //the first time 1C
            if(count1C ==1){
                Iterator<Integer> Iterator = notguess.iterator();
                while(Iterator.hasNext()){
                    int e = Iterator.next();
                    guessIndex = ran.nextInt(1);
                    if(e == guess[1 - guessIndex]){
                        Iterator.remove();

                    }
                }
                removedAns.add(guess[1 - guessIndex]);
            }else {
                Iterator<Integer> Iterator = notguess.iterator();
                while(Iterator.hasNext()) {
                    int e = Iterator.next();
                    if (e == guess[1 - guessIndex]) {
                        Iterator.remove();

                    }
                }
                removedAns.add(guess[1 - guessIndex]);
            }
            Log.i("removedans:",String.valueOf(removedAns));
            return StartGuessing(notguess);
        }
        else {

            return "我猜是"+String.valueOf(guess[0])+" "+String.valueOf(guess[1])+"答對了2C!! ^ ^ ";
        }

    }


}
