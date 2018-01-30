package com.shreyas.retrofit.helper;

/**
 * Created by User on 30-01-2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.shreyas.retrofit.R;

import java.util.ArrayList;


public class ListViewAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<String> name;
    private final ArrayList<String> num;
    private final ArrayList<String> password;
    private LayoutInflater layoutInflater;

    public ListViewAdapter(Context ctx, ArrayList<String> name, ArrayList<String> num, ArrayList<String> password) {
        this.context = ctx;
        this.name = name;
        this.num = num;
        this.password = password;


    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        Holder holder = new Holder();
        layoutInflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        view = layoutInflater.inflate(R.layout.list_item, null);
        holder.txt_name = (TextView) view.findViewById(R.id.name);
        holder.txt_num = (TextView) view.findViewById(R.id.num);
        holder.txt_password = (TextView) view.findViewById(R.id.password);


        holder.txt_name.setText(name.get(position));
        holder.txt_num.setText(name.get(position));
        holder.txt_password.setText(password.get(position));


        return view;
    }

    static class Holder {
        TextView txt_name, txt_num, txt_password;
    }
}