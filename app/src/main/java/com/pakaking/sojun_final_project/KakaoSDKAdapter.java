package com.pakaking.sojun_final_project;

import android.content.Context;

import com.kakao.auth.*;

/**
 * Created by tlstk on 2017-10-02.
 */

public class KakaoSDKAdapter extends KakaoAdapter {

    @Override
    public IApplicationConfig getApplicationConfig() {
        return new IApplicationConfig() {

            @Override
            public Context getApplicationContext() {
                return GlobalApplication.getGlobalApplicationContext();
            }
        };
    }

    @Override
    public ISessionConfig getSessionConfig() {
        return new ISessionConfig() {
            @Override
            public AuthType[] getAuthTypes() {
                return new AuthType[]{AuthType.KAKAO_LOGIN_ALL}; //여기서타입을 변경가능합니다~
            }

            @Override
            public boolean isUsingWebviewTimer() {
                return false;
            }

            @Override
            public boolean isSecureMode() {
                return false;
            }

            @Override
            public ApprovalType getApprovalType() {
                return ApprovalType.INDIVIDUAL;
            }

            @Override
            public boolean isSaveFormData() {
                return true;
            }
        };
    }
}
