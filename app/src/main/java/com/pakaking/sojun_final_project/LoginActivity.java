package com.pakaking.sojun_final_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

/**
 * Created by tlstk on 2017-09-30.
 */

public class LoginActivity extends AppCompatActivity {

    private com.kakao.usermgmt.LoginButton btnKakao;
    private SessionCallback callback;
    long pressTime;
    public KakaoSDK KakaoTalkResponseCallback;
    public KakaoSDK getProfileImageURL;

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - pressTime < 2000) {
            finishAffinity();
            return;
        }
        Toast.makeText(this, "한 번 더 누르시면 앱이 종료됩니다", Toast.LENGTH_LONG).show();
        pressTime = System.currentTimeMillis();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);

        btnKakao = (com.kakao.usermgmt.LoginButton) findViewById(R.id.kakaologin);

        com.kakao.auth.Session.getCurrentSession().checkAndImplicitOpen();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            KakaorequestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.d("ErrorSession", exception.getMessage());
            }
        }
    }

    public void KakaorequestMe() {

        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.d("Error", "오류로 카카오로그인 실패 ");
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("Error", "오류로 카카오로그인 실패 ");
            }

            @Override
            public void onNotSignedUp() {
            }

            @Override
            public void onSuccess(UserProfile userProfile) {

                Log.e("UserProfile", userProfile.toString());

                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                vibe.vibrate(300);
                overridePendingTransition(R.anim.start_enter, R.anim.start_enter);
                finish();

            }
        });
    }

}



