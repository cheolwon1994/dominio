package com.example.dominio;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity {

    private String student_id;

    Button btnTrainProgram;
    Button btnRecord;
    Button btnModify;
    Button btnBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Intent intent = getIntent();
        student_id = intent.getStringExtra("id");


        btnTrainProgram = (Button)findViewById(R.id.btnTrainProgram);
        btnRecord = (Button)findViewById(R.id.btnRecord);
        btnModify=(Button)findViewById(R.id.btnModify);
        btnBoard = (Button)findViewById(R.id.btnBoard);

        btnTrainProgram.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TpActivity.class);
                intent.putExtra("id",student_id);
                startActivity(intent);
            }

        });

        /*btnBoard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BoardActivity.class);
                intent.putExtra("id",student_id);
                startActivity(intent);
            }

        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ClubListActivity.class);
                startActivity(intent);
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FestivalActivity.class);
                intent.putExtra("id",student_id);
                startActivity(intent);
            }

        });

        btnBoard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FestivalActivity.class);
                intent.putExtra("id",student_id);
                startActivity(intent);
            }

        });
        */

    }
}

