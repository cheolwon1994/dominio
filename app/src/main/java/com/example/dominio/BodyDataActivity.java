package com.example.dominio;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class BodyDataActivity extends AppCompatActivity {

    private String student_id;
    protected String latest_Date;

    String myJSON;
    ListView listView;
    BodyDataListAdapter bodyDataAdapter;
    ArrayList<list_bodyData> list_bodyDataArrayList;

    //수정
    private static final String TAG_RESULTS = "result";
    private static final String TAG_Height = "Height";
    private static final String TAG_Weight = "Weight";
    private static final String TAG_Muscle = "Muscle";
    private static final String TAG_Fat = "Fat";
    private static final String TAG_Date = "Date";

    JSONArray list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodydata);

        //id 받음
        Intent intent = getIntent();
        student_id = intent.getStringExtra("id");
        Log.i("balbalbal",student_id);
        listView = (ListView) findViewById(R.id.BodyDataBoard);
        list_bodyDataArrayList = new ArrayList<list_bodyData>();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //수정
                Intent result = new Intent();
                result.putExtra("Height",list_bodyDataArrayList.get(position).getUser_height());
                result.putExtra("Weight",list_bodyDataArrayList.get(position).getUser_weight());
                result.putExtra("Muscle",list_bodyDataArrayList.get(position).getUser_muscle() );
                result.putExtra("Fat",list_bodyDataArrayList.get(position).getUser_fat());
                setResult(RESULT_OK, result);
                finish();

            }
        });

        getData("http://15.164.163.148/dominio/getbodyData.php",student_id);
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            list = jsonObj.getJSONArray(TAG_RESULTS);
            Log.i("balbalbal5",String.valueOf(list.length()));
            for (int i = 0; i < list.length(); i++) {
                JSONObject c = list.getJSONObject(i);
                String Height = c.getString(TAG_Height);
                String Weight = c.getString(TAG_Weight);
                String Muscle = c.getString(TAG_Muscle);
                String Fat = c.getString(TAG_Fat);
                String Date = c.getString(TAG_Date);
                Log.i("abcdabcdabcd",Date);
                if(latest_Date==null){
                    latest_Date = Date;
                }else{
                    calDate(latest_Date, Date);
                    Log.i("abcabcabcabc2",latest_Date);
                }
                list_bodyData lists = new list_bodyData(R.mipmap.ic_launcher,"사용자 아이디 "+"\n"+student_id,"키 "+"\t" +Height, "몸무게 " + "\t" + Weight, "근육량 " + "\t"+ Muscle, "지방량 " + "\t" + Fat, "측정날짜 " + "\t"+Date);
                list_bodyDataArrayList.add(lists);
            }

            //최종 데이터 출력
            Log.i("abcabcabcabc3",latest_Date);

            //안해도됨
            //Collections.reverse(list_tpArrayList);
            bodyDataAdapter = new BodyDataListAdapter(BodyDataActivity.this,list_bodyDataArrayList);
            listView.setAdapter(bodyDataAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void calDate(String date1, String date2){
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");

            Date FirstDate = format.parse(date1);
            Date SecondDate = format.parse(date2);

            long calDate = FirstDate.getTime() - SecondDate.getTime();
            if (calDate <0)
                latest_Date = date2;
        }
        catch(ParseException e)
        {
            // 예외 처리
        }
    }

    public void getData(String url, String student_id) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {


                BufferedReader bufferedReader = null;
                try {

                    String uri = params[0];
                    String student_id = params[1];
                    Log.i("balbalbal2",student_id);
                    String data = URLEncoder.encode("student_id","UTF-8")+"="+URLEncoder.encode(student_id,"UTF-8");

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
        g.execute(url,student_id);
    }
    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent();
        intent2.putExtra("result","OK");
        setResult(1234, intent2);
        finish();
    }

}
