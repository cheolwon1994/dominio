package com.example.dominio;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

public class SuggestTpActivity extends AppCompatActivity {

    private String student_id;
    private String Height;
    private String Weight;
    private String Muscle;
    private String Fat;


    private int type=0;             //운동추천 알고리즘
    private int cnt=0;                //4가 되면 화면에 표시

    private String bodyPart;
    private String ExerciseType;

    String myJSON;
    ListView listView;
    TsListAdapter tsAdapter;
    ArrayList<list_ts> list_tsArrayList;
    ArrayList<list_ts> list_tsArrayList2;

    Button Addts;


    private static final String TAG_RESULTS = "result";
    private static final String TAG_ExerciseID = "ExerciseID";

    JSONArray list = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ts);

        //id 받음
        Intent intent = getIntent();
        student_id = intent.getStringExtra("id");
        Height = intent.getStringExtra("height");
        Weight = intent.getStringExtra("weight");
        Muscle = intent.getStringExtra("muscle");
        Fat = intent.getStringExtra("fat");

        listView = (ListView) findViewById(R.id.TsBoard);

        list_tsArrayList = new ArrayList<list_ts>();
        list_tsArrayList2= new ArrayList<list_ts>();


        //알고리즘
        cal();

//        Log.i("aaaaaaaaaaaaaaaa",String.valueOf(type));
//        Log.i("aaaaaaaaaaaaaaaaa2",bodyPart);
//        Log.i("aaaaaaaaaaaaaaaaa3",ExerciseType);

        //************데이터베이스에 추가
        Addts=(Button)findViewById(R.id.btnAdd);
        Addts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent();
                result.putExtra("ei1",list_tsArrayList2.get(0).getTs_name());
                result.putExtra("ei2",list_tsArrayList2.get(1).getTs_name());
                result.putExtra("ei3",list_tsArrayList2.get(2).getTs_name() );
                result.putExtra("ei4",list_tsArrayList2.get(3).getTs_name());
                setResult(RESULT_OK, result);
                finish();
            }
        });

        for(int i=0;i<4;i++) {
            setType(i);
            getData("http://15.164.163.148/dominio/suggestTp.php", bodyPart, ExerciseType);
        }

    }
    public void setType(int i){
        if(i==0) {
            switch (type) {
                case 1:
                    bodyPart = "All";
                    ExerciseType = "Cardio";
                    break;
                case 2:
                    bodyPart = "Up";
                    ExerciseType = "Strength";
                    break;
                case 3:
                    bodyPart = "Up";
                    ExerciseType = "Strength";
                    break;
                case 4:
                    bodyPart = "Up";
                    ExerciseType = "Strength";
                    break;
                case 5:
                    bodyPart = "Up";
                    ExerciseType = "Strength";
                    break;
            }
        }else if(i==1){
            switch (type) {
                case 1:
                    bodyPart = "Up";
                    ExerciseType = "Strength";
                    break;
                case 2:
                    bodyPart = "Down";
                    ExerciseType = "Strength";
                    break;
                case 3:
                    bodyPart = "Down";
                    ExerciseType = "Strength";
                    break;
                case 4:
                    bodyPart = "Down";
                    ExerciseType = "Strength";
                    break;
                case 5:
                    bodyPart = "Down";
                    ExerciseType = "Strength";
                    break;
            }
        }else if(i==2){
            switch (type) {
                case 1:
                    bodyPart = "Down";
                    ExerciseType = "Strength";
                    break;
                case 2:
                    bodyPart = "All";
                    ExerciseType = "Cardio";
                    break;
                case 3:
                    bodyPart = "Up";
                    ExerciseType = "Strength";
                    break;
                case 4:
                    bodyPart = "All";
                    ExerciseType = "Cardio";
                    break;
                case 5:
                    bodyPart = "All";
                    ExerciseType = "Strength";
                    break;
            }
        }else if(i==3){
            switch (type) {
                case 1:
                    bodyPart = "All";
                    ExerciseType = "Stretching";
                    break;
                case 2:
                    bodyPart = "All";
                    ExerciseType = "Cardio";
                    break;
                case 3:
                    bodyPart = "Down";
                    ExerciseType = "Strength";
                    break;
                case 4:
                    bodyPart = "All";
                    ExerciseType = "Stretching";
                    break;
                case 5:
                    bodyPart = "All";
                    ExerciseType = "Stretching";
                    break;
            }
        }
    }
    public void cal(){
        if(Integer.parseInt(Height) - 95 < Integer.parseInt(Weight)) {        //과체중
            if(Integer.parseInt(Muscle)>=Integer.parseInt(Fat))
                type = 1;           //과체중 근육형
            else
                type = 2;           //과체중 지방형
        }
        else if(Integer.parseInt(Weight) < Integer.parseInt(Height) - 105){     //저체중
            type = 5;               //저체중
        }else{                                                                  //표준체중
            if(Integer.parseInt(Muscle) >= Integer.parseInt(Fat))
                type = 3;           //표준 근육형
            else
                type = 4;           //표준 지방형
        }
    }

    protected void showList() {
        try {
            list_tsArrayList.clear();
            JSONObject jsonObj = new JSONObject(myJSON);
            list = jsonObj.getJSONArray(TAG_RESULTS);
            Log.i("balbalbal5",String.valueOf(list.length()));


            for (int i = 0; i < list.length(); i++) {
                JSONObject c = list.getJSONObject(i);
                String ExerciseID = c.getString(TAG_ExerciseID);
                list_ts lists = new list_ts(R.mipmap.ic_launcher,"운동 이름 "+"\n"+ExerciseID);
                list_tsArrayList.add(lists);
                Log.i("hhhhhhhhhhhhhhhhhhh",lists.getTs_name());
            }
            //안해도됨
            //Collections.reverse(list_tpArrayList);
            cnt++;
            Log.i("eeeeeeeeeeeeeeeeeeeee","cnt값 : "+String.valueOf(cnt));
            Log.i("eeeeeeeeeeeeeeeeeeeee2","list길이 : "+list_tsArrayList.size());
            Collections.shuffle(list_tsArrayList);
            list_tsArrayList2.add(list_tsArrayList.get(0));
            Log.i("ccccccccccccccccccccc",list_tsArrayList.get(0).getTs_name());
            if(cnt==4) {
                Log.i("bbbbbbbbbbbbbbbbbb","if문 진입");
                tsAdapter = new TsListAdapter(SuggestTpActivity.this, list_tsArrayList2);
                listView.setAdapter(tsAdapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getData(String url, String bodyPart, String ExerciseType) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                BufferedReader bufferedReader = null;
                try {

                    String uri = params[0];
                    String bodyPart= params[1];
                    String ExerciseType= params[2];

                    String data = URLEncoder.encode("bodyPart","UTF-8")+"="+URLEncoder.encode(bodyPart,"UTF-8");
                    data += "&" + URLEncoder.encode("ExerciseType","UTF-8")+"="+URLEncoder.encode(ExerciseType,"UTF-8");

                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    wr.write(data);
                    wr.flush();

                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url,bodyPart,ExerciseType);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
