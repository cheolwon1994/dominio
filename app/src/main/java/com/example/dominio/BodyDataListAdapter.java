package com.example.dominio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BodyDataListAdapter extends BaseAdapter {

    Context context;
    ArrayList<list_bodyData> list_bodyDataArrayList;
    ViewHolder viewHolder;

    class ViewHolder{

        TextView user_id;
        TextView user_height;
        TextView user_weight;
        TextView user_muscle;
        TextView user_fat;
        TextView user_date;
        ImageView image_user;

    }

    public BodyDataListAdapter(Context context, ArrayList<list_bodyData> list_bodyDataArrayList) {
        this.context = context;
        this.list_bodyDataArrayList = list_bodyDataArrayList;
    }

    @Override
    public int getCount() {
        return this.list_bodyDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_bodyDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.bodydata,null);

            viewHolder = new ViewHolder();

            viewHolder.image_user = (ImageView)convertView.findViewById(R.id.image_user);
            viewHolder.user_id = (TextView)convertView.findViewById(R.id.text_user_id);
            viewHolder.user_height = (TextView)convertView.findViewById(R.id.text_user_height);
            viewHolder.user_weight = (TextView)convertView.findViewById(R.id.text_user_weight);
            viewHolder.user_muscle = (TextView)convertView.findViewById(R.id.text_user_muscle);
            viewHolder.user_fat = (TextView)convertView.findViewById(R.id.text_user_fat);
            viewHolder.user_date = (TextView)convertView.findViewById(R.id.text_user_date);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //수정필요
        viewHolder.image_user.setImageResource(list_bodyDataArrayList.get(position).getImage_user());
        viewHolder.user_id.setText(list_bodyDataArrayList.get(position).getUser_id());
        viewHolder.user_height.setText(list_bodyDataArrayList.get(position).getUser_height());
        viewHolder.user_weight.setText(list_bodyDataArrayList.get(position).getUser_weight());
        viewHolder.user_muscle.setText(list_bodyDataArrayList.get(position).getUser_muscle());
        viewHolder.user_fat.setText(list_bodyDataArrayList.get(position).getUser_fat());
        viewHolder.user_date.setText(list_bodyDataArrayList.get(position).getUser_date());

        return convertView;
    }


}
