package com.pakaking.sojun_final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.network.ErrorResult;
import com.kakao.util.helper.log.Logger;

import java.util.HashMap;
import java.util.Map;

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


    public class KakaoTalkMainActivity extends Activity {
        private void redirectLoginActivity() {
            Intent intent = new Intent(this, KakaoTalkLoginActivity.class);
            startActivity(intent);
            finish();
        }

        private class KakaoTalkLoginActivity {
        }

        private abstract class KakaoTalkResponseCallback<T> extends TalkResponseCallback<T> {


            @Override
            public void onNotKakaoTalkUser() {
                Logger.w("not a KakaoTalk user");
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e("failure : " + errorResult);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                redirectSignupActivity();
            }

            private void redirectSignupActivity() {
            }
        }
    }



    class KakaoTalkMessageBuilder {
        public Map<String, String> messageParams = new HashMap<String, String>();

        public KakaoTalkMessageBuilder addParam(String key, String value) {
            messageParams.put("${" + key + "}", value);
            return this;
        }

        public Map<String, String> build() {
            return messageParams;
        }
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