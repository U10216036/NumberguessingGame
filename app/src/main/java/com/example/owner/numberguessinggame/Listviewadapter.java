package com.example.owner.numberguessinggame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//listview adapter
public class Listviewadapter extends BaseAdapter {
    private Context context;
    private ArrayList<Answer> ans;

    public Listviewadapter(Context context, ArrayList<Answer> items) {
        this.context = context;
        this.ans = items;
    }

    @Override
    public int getCount() {
        return ans.size();
    }

    @Override
    public Object getItem(int position) {
        return ans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Answer currentItem = (Answer) getItem(position);
        viewHolder.itemName.setText(currentItem.getAnswer());


        return convertView;
    }


    private class ViewHolder {
        TextView itemName;

        public ViewHolder(View view) {
            itemName = (TextView)view.findViewById(R.id.textView1);
        }
    }

    public void clearAnswer() {
        ans.clear();
    }
}
