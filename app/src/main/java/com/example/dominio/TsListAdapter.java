package com.example.dominio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TsListAdapter extends BaseAdapter {

    Context context;
    ArrayList<list_ts> list_tsArrayList;
    ViewHolder viewHolder;

    class ViewHolder{

        TextView ts_name;
        ImageView image_ts;

    }

    public TsListAdapter(Context context, ArrayList<list_ts> list_tsArrayList) {
        this.context = context;
        this.list_tsArrayList = list_tsArrayList;
    }

    @Override
    public int getCount() {
        return this.list_tsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_tsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.ts,null);

            viewHolder = new ViewHolder();
            //수정필요
            viewHolder.ts_name = (TextView)convertView.findViewById(R.id.text_ts_name);
            viewHolder.image_ts = (ImageView)convertView.findViewById(R.id.image_ts);
            //
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //수정필요
        viewHolder.ts_name.setText(list_tsArrayList.get(position).getTs_name());
        viewHolder.image_ts.setImageResource(list_tsArrayList.get(position).getImage_ts());
        //
        return convertView;
    }


}
