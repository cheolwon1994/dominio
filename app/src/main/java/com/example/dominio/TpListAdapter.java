package com.example.dominio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TpListAdapter extends BaseAdapter {

    Context context;
    ArrayList<list_tp> list_tpArrayList;
    ViewHolder viewHolder;

    class ViewHolder{

        TextView tp_name;
        TextView tp_trainTime;
        TextView tp_success;
        //TextView fest_date;
        //TextView fest_club;
        ImageView image_tp;

    }

    public TpListAdapter(Context context, ArrayList<list_tp> list_tpArrayList) {
        this.context = context;
        this.list_tpArrayList = list_tpArrayList;
    }

    @Override
    public int getCount() {
        return this.list_tpArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_tpArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.tp,null);

            viewHolder = new ViewHolder();

            viewHolder.tp_name = (TextView)convertView.findViewById(R.id.text_tp_name);
            viewHolder.image_tp = (ImageView)convertView.findViewById(R.id.image_tp);
            viewHolder.tp_trainTime = (TextView)convertView.findViewById(R.id.text_trainTime);
            viewHolder.tp_success = (TextView)convertView.findViewById(R.id.text_success);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.tp_name.setText(list_tpArrayList.get(position).getTp_name());
        viewHolder.tp_trainTime.setText(list_tpArrayList.get(position).getTp_trainTime());
        viewHolder.tp_success.setText(list_tpArrayList.get(position).getTp_success());
        viewHolder.image_tp.setImageResource(list_tpArrayList.get(position).getImage_tp());

        return convertView;
    }


}
