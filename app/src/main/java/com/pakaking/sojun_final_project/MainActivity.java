package com.pakaking.sojun_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.pakaking.sojun_final_project.R.id.check;

public class MainActivity extends AppCompatActivity {

    public String start, end;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText_Start = (EditText) findViewById(R.id.edit_start);
        final EditText editText_End = (EditText) findViewById(R.id.edit_end);
        final Button bt = (Button) findViewById(check);

        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String start = editText_Start.getText().toString();
                final String end = editText_End.getText().toString();

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("출발역",start);
                intent.putExtra("도착역",end);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("start", start);
        outState.putString("end", end);
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