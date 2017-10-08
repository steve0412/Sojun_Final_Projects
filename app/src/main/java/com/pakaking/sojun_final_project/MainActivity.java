package com.pakaking.sojun_final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.network.ErrorResult;
import com.kakao.util.helper.log.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.pakaking.sojun_final_project.R.id.check;

public class MainActivity extends AppCompatActivity {

    public String strJson;
    String start, end;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText_Start = (EditText) findViewById(R.id.edit_start);
        final EditText editText_End = (EditText) findViewById(R.id.edit_end);
        final Button bt = (Button) findViewById(check);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://swopenAPI.seoul.go.kr/api/subway/4e53584864746c7334355254446672/json/shortestRoute/0/5/남영/구파발";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        strJson = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                output.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);

        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                start = editText_Start.getText().toString();
                end = editText_End.getText().toString();

                String OutputData = "";
                JSONObject jsonResponse, jsonResponseOri;

                try {
                    /****** 이름/값으로 JSON 객채를 생성 ********/
                    jsonResponseOri = new JSONObject(strJson);
                    String jsonResponseStr = jsonResponseOri.getString("PublicWiFiPlaceInfo");
                    jsonResponse = new JSONObject(jsonResponseStr);
                    /****** 각각의 스트링 값을 읽어오자 ********/
                    String list_total_count = jsonResponse.getString("list_total_count");
                    String RESULT = jsonResponse.getString("RESULT");
                    String row = jsonResponse.getString("row");
                    /**
                     * 우리가 실제 필요로 하는 값을 JSON 배열로 가져온다.
                     */
                    JSONArray jsonMainNode = new JSONArray(row);

                    Log.i("JSON parse", "" + jsonMainNode.length() + "");
                    /**
                     * 실제 배열의 값이 0보다 클경우 실행
                     */
                    if (jsonMainNode.length() > 0) {
                        for (int i = 0; i < jsonMainNode.length(); i++) {
                            JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                            String GU_NM = jsonChildNode.optString("GU_NM").toString();
                            String CATEGORY = jsonChildNode.optString("CATEGORY").toString();
                            String PLACE_NAME = jsonChildNode.optString("PLACE_NAME").toString();
                            String INSTL_X = jsonChildNode.optString("INSTL_X").toString();
                            String INSTL_Y = jsonChildNode.optString("INSTL_Y").toString();
                            String INSTL_DIV = jsonChildNode.optString("INSTL_DIV").toString();

                            OutputData += "구번호:" + GU_NM + ", 카테고리:" + CATEGORY + ", 장소:" + PLACE_NAME +
                                    ", X좌표:" + INSTL_X + ", Y좌표:" + INSTL_Y + ", 장소구분:" + INSTL_DIV + "\n\n";
                        }
                    }
                    /************ Show Output on screen/activity **********/
                    output.setText(OutputData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
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