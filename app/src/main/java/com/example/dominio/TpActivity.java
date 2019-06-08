package com.example.dominio;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

public class TpActivity extends AppCompatActivity {

    private String student_id;
    private String Height;
    private String Weight;
    private String Muscle;
    private String Fat;

    private String ei1;
    private String ei2;
    private String ei3;
    private String ei4;

    private int flag=0;

    String myJSON;
    ListView listView;
    TpListAdapter tpAdapter;
    ArrayList<list_tp> list_tpArrayList;


    Button Addtp;
    Button bData;

    //수정
    private static final String TAG_RESULTS = "result";
    private static final String TAG_TrainingSetID = "TrainingSetID";
    private static final String TAG_TrainingTime = "TrainingTime";
    private static final String TAG_success = "success";

    JSONArray list = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tp);

        //id 받음
        Intent intent = getIntent();
        student_id = intent.getStringExtra("id");
        Log.i("balbalbal",student_id);
        listView = (ListView) findViewById(R.id.TpBoard);
        list_tpArrayList = new ArrayList<list_tp>();

        /*  Set를 눌렀을 때 기댓값 //현재는 필요없음 (Retrieve를 하지 않기 때문)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //수정
                Intent intent = new Intent(getApplicationContext(),PostFestActivity.class);
                intent.putExtra("fest_name",list_tpArrayList.get(position).getFest_name());
                intent.putExtra("fest_club",list_tpArrayList.get(position).getFest_club());
                intent.putExtra("fest_date",list_tpArrayList.get(position).getFest_date());
                intent.putExtra("fest_describe",list_tpArrayList.get(position).getFest_describe());
                startActivity(intent);

            }
        });*/

        //추가
        Addtp=(Button)findViewById(R.id.btnAdd);
        Addtp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(flag== 0) {
                    Toast.makeText(TpActivity.this, "정보를 선택하세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(flag==1) {
                    //수정필요
                    Intent intent = new Intent(getApplicationContext(), SuggestTpActivity.class);
                    intent.putExtra("id", student_id);
                    intent.putExtra("height",Height);
                    intent.putExtra("weight",Weight);
                    intent.putExtra("muscle",Muscle);
                    intent.putExtra("fat",Fat);
                    startActivityForResult(intent,2000);
                    //
                }
            }

        });

        //정보선택
        bData=(Button)findViewById(R.id.btnbData);
        bData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                flag=0;
                Intent intent = new Intent(getApplicationContext(),BodyDataActivity.class);
                intent.putExtra("id",student_id);
                startActivityForResult(intent,1000);

            }

        });

        //수정필요
        getData("http://15.164.163.148/dominio/getTp.php",student_id);
    }


    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            list = jsonObj.getJSONArray(TAG_RESULTS);
            Log.i("balbalbal5",String.valueOf(list.length()));
            for (int i = 0; i < list.length(); i++) {
                JSONObject c = list.getJSONObject(i);
                String TrainingSetID = c.getString(TAG_TrainingSetID);
                String TrainingTime = c.getString(TAG_TrainingTime);
                String success = c.getString(TAG_success);
                Log.i("balbalbal3",TrainingSetID);
                Log.i("balbalbal3",success);
                list_tp lists = new list_tp(R.mipmap.ic_launcher,"트레이닝 세트 번호 "+"\n"+TrainingSetID,"운동 시간 "+"\n" +TrainingTime, "실행 여부 " + "\n" + success);
                list_tpArrayList.add(lists);
            }
            //안해도됨
            //Collections.reverse(list_tpArrayList);
            tpAdapter = new TpListAdapter(TpActivity.this,list_tpArrayList);
            listView.setAdapter(tpAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            flag=1;
            int idx;
            Height = data.getStringExtra("Height");
            idx = Height.indexOf("\t");
            Height = Height.substring(idx+1);

            Weight = data.getStringExtra("Weight");
            idx = Weight.indexOf("\t");
            Weight = Weight.substring(idx+1);

            Muscle = data.getStringExtra("Muscle");
            idx = Muscle.indexOf("\t");
            Muscle = Muscle.substring(idx+1);

            Fat = data.getStringExtra("Fat");
            idx = Fat.indexOf("\t");
            Fat = Fat.substring(idx+1);

//            Log.i("llllllllllllllllllll1",Height);
//            Log.i("llllllllllllllllllll2",Weight);
//            Log.i("llllllllllllllllllll3",Muscle);
//            Log.i("llllllllllllllllllll4",Fat);


        }
        else if(requestCode==2000){
            int idx;
            ei1 = data.getStringExtra("ei1");
            idx = ei1.indexOf("\n");
            ei1 = ei1.substring(idx+1);

            ei2 = data.getStringExtra("ei2");
            idx = ei2.indexOf("\n");
            ei2 = ei2.substring(idx+1);

            ei3 = data.getStringExtra("ei3");
            idx = ei3.indexOf("\n");
            ei3 = ei3.substring(idx+1);

            ei4 = data.getStringExtra("ei4");
            idx = ei4.indexOf("\n");
            ei4 = ei4.substring(idx+1);
            Log.i("kkkkkkkkkkkkkkkkkk1","ei1 : "+ei1);
            Log.i("kkkkkkkkkkkkkkkkkk2","ei2 : "+ei2);
            Log.i("kkkkkkkkkkkkkkkkkk3","ei3 : "+ei3);
            Log.i("kkkkkkkkkkkkkkkkkk4","ei4 : "+ei4);
            showData(student_id, ei1,ei2,ei3,ei4);

        }
    }
    public void showData(String student_id, String ei1, String ei2, String ei3, String ei4){

        class InsetData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected  String doInBackground(String... params)
            {
                try{
                    String student_id = (String)params[0];
                    String ei1 = (String)params[1];
                    String ei2 = (String)params[2];
                    String ei3 = (String)params[3];
                    String ei4 = (String)params[4];

                    //********************URL 수정필요
                    String link = "http://15.164.163.148/dominio/addTp.php";

                    String data = URLEncoder.encode("student_id","UTF-8")+"="+URLEncoder.encode(student_id,"UTF-8");
                    data += "&" + URLEncoder.encode("ei1","UTF-8")+"="+URLEncoder.encode(ei1,"UTF-8");
                    data += "&" + URLEncoder.encode("ei2","UTF-8")+"="+URLEncoder.encode(ei2,"UTF-8");
                    data += "&" + URLEncoder.encode("ei3","UTF-8")+"="+URLEncoder.encode(ei3,"UTF-8");
                    data += "&" + URLEncoder.encode("ei4","UTF-8")+"="+URLEncoder.encode(ei4,"UTF-8");


                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    Log.i("log22",data);

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e)
                {
                    return new String("Exception: "+e.getMessage());
                }

            }
        }

        InsetData task = new InsetData();
        task.execute(student_id,ei1,ei2,ei3,ei4);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
