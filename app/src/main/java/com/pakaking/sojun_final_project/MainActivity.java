package com.pakaking.sojun_final_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kakao.auth.KakaoSDK;
import com.kakao.kakaotalk.KakaoTalkService;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.response.KakaoTalkProfile;
import com.kakao.network.ErrorResult;
import com.kakao.util.helper.log.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity {

    String start, end;
    EditText editText_Start, editText_End;


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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_Start = (EditText) findViewById(R.id.edit_start);
        editText_End = (EditText) findViewById(R.id.edit_end);


        ImageButton to_me = (ImageButton) findViewById(R.id.to_me);
        to_me.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                start = editText_Start.getText().toString();
                end = editText_End.getText().toString();

                public void requestSendMemo() {
                    String message = "Test for send Memo";
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("''yy년 MM월 dd일 E요일");

                    KakaoTalkMessageBuilder builder = new KakaoTalkMessageBuilder();
                    builder.addParam("MESSAGE", message);
                    builder.addParam("DATE", sdf.format(date));

                    KakaoTalkService.requestSendMemo(new LoginActivity().KakaoTalkResponseCallback<Boolean>() {
                        public void onSuccess(Boolean String result; result) {
                            Logger.d("send message to my chatroom : " + result);
                             }
                        }
                            , "6019"
                            , builder.build());
            }

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