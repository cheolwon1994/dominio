package com.example.dominio;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button btnRegist;
    private Button btnLogin;
    private EditText student_id;
    private EditText password;
    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_Student_id = "student_id";
    private static final String TAG_pwd = "pwd";
    JSONArray peoples = null;
    //ArrayList<HashMap<String, String>> personList;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        student_id = (EditText) findViewById(R.id.student_id);
        password = (EditText) findViewById(R.id.password);

        btnRegist = (Button) findViewById(R.id.btnRegist);
        /*btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistActivity.class);
                startActivityForResult(intent,1000);
            }
        });*/

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if( student_id.getText().toString().length() == 0 ) {
                    Toast.makeText(MainActivity.this, "학번을 입력하세요!", Toast.LENGTH_SHORT).show();
                    student_id.requestFocus();
                    return;
                }

                // 비밀번호 입력 확인
                if( password.getText().toString().length() == 0 ) {
                    Toast.makeText(MainActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                    return;
                }
                Log.i("before","before");

                getData("http://52.11.180.128/dbProject/1.php"); //수정 필요
                Log.i("after","after");

/*
                if(flag == 1)
                {
                    flag = 0;
                    Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                    startActivityForResult(intent,2000);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "잘못입력했습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

*/
            }
        });
    }//on create

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_Student_id);
                String pwd = c.getString(TAG_pwd);

                if((id.equals(student_id.getText().toString())) && (pwd.equals(password.getText().toString())))
                {flag = 1; break;}
            }

            Log.i("finish","finish");

            /*if(flag == 1)
            {
                flag = 0;
                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);

                //사용자 정보 넘김
                intent.putExtra("id",student_id.getText().toString());

                startActivityForResult(intent,2000);
            }
            else
            {
                Toast.makeText(MainActivity.this, "잘못입력했습니다.", Toast.LENGTH_SHORT).show();
                return;
            }*/



            return ;

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
        g.execute(url);
    }
}