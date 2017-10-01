package com.pakaking.sojun_final_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String start, end;
    EditText editText_Start, editText_End;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_Start = (EditText)findViewById(R.id.edit_start);
        editText_End = (EditText)findViewById(R.id.edit_end);

        ImageButton to_me = (ImageButton)findViewById(R.id.to_me);
        to_me.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                start = editText_Start.getText().toString();
                end = editText_End.getText().toString();

                Toast.makeText(getApplicationContext(), "출발역 : " + start + " 도착역 : " + end + " 저장 완료!", Toast.LENGTH_SHORT).show();
            }
        });

        if (savedInstanceState != null) {
            start = savedInstanceState.getString("start");
            end = savedInstanceState.getString("end");

            Toast.makeText(getApplicationContext(), "출발역 : " + start + " 도착역 : " + end + " 복원 완료!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("start",start);
        outState.putString("end",end);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}