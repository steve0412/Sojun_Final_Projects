package com.pakaking.sojun_final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.kakaotalk.KakaoTalkService;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.network.ErrorResult;
import com.kakao.util.helper.log.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.pakaking.sojun_final_project.R.id.minStatn_;
import static com.pakaking.sojun_final_project.R.id.minTm_;
import static com.pakaking.sojun_final_project.R.id.minTransfer_;
import static com.pakaking.sojun_final_project.R.id.shtStatn_;
import static com.pakaking.sojun_final_project.R.id.shtTm_;
import static com.pakaking.sojun_final_project.R.id.shtTransfer_;
import static com.pakaking.sojun_final_project.R.id.toto;

/**
 * Created by tlstk on 2017-10-06.
 */

public class ResultActivity extends AppCompatActivity {


    private String apiKey;
    private String start_station;
    private String end_station;
    private List<ShortestRouteList> stationList;

    private TextView sht_Tm;
    private TextView min_Tm;
    private TextView sht_Transfer;
    private TextView min_Transfer;
    private TextView sht_Statn;
    private TextView min_Statn;
    private String kakaoMsg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        sht_Tm = (TextView) findViewById(shtTm_);
        min_Tm = (TextView) findViewById(minTm_);
        sht_Transfer = (TextView) findViewById(shtTransfer_);
        min_Transfer = (TextView) findViewById(minTransfer_);
        sht_Statn = (TextView) findViewById(shtStatn_);
        min_Statn = (TextView) findViewById(minStatn_);


        Intent intent = getIntent();
        String start = intent.getStringExtra("출발역");
        String end = intent.getStringExtra("도착역");


        apiKey = "4e53584864746c7334355254446672";
        start_station = start;
        end_station = end;

        TextView to_to = (TextView) findViewById(toto);
        to_to.setText(start + "역  --->  " + end + "역");

         setStnListAPI(apiKey, start_station, end_station);

        ImageButton tome = (ImageButton) findViewById(R.id.to_me);

        tome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestSendMemo();
                //finish();
            }
        });
    }




    public void setStnListAPI(String apiKey, final String start_station, final String end_station) {

        SubwayApiService api = ServiceGenerator.getListApiService();

        api.getStationList(apiKey, start_station, end_station).enqueue(new Callback<Station>() {

            @Override
            public void onResponse(Call<Station> call, Response<Station> response) {

                if (response.isSuccessful()) {
                    stationList = response.body().getShortestRouteList();
                    sht_Tm.setText(stationList.get(0).getShtTravelTm() + "분");
                    min_Tm.setText(stationList.get(0).getMinTravelTm() + "분");
                    sht_Transfer.setText(stationList.get(0).getShtTransferCnt() + "번");
                    min_Transfer.setText(stationList.get(0).getMinTransferCnt() + "번");
                    sht_Statn.setText(stationList.get(0).getShtStatnCnt() + "개");
                    min_Statn.setText(stationList.get(0).getMinStatnCnt() + "개");
                    kakaoMsg = stationList.get(0).getMinTravelMsg();
                } else {
                    Log.v("SearchActivity", start_station);
                    Log.v("SearchActivity2", end_station);
                    Toast.makeText(getApplicationContext(), start_station + " and " + end_station + " response fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Station> call, Throwable t) {
                Log.v("SearchActivity", "onFailure" + start_station);
                Log.v("SearchActivity2", "onFailure" + end_station);
                Toast.makeText(getApplicationContext(), start_station + " and " + end_station + " ronFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestSendMemo() {
        String message = kakaoMsg;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("''yy년 MM월 dd일 E요일");
        KakaoTalkMainActivity.KakaoTalkMessageBuilder builder = new KakaoTalkMainActivity.KakaoTalkMessageBuilder();
        builder.addParam("MESSAGE", message);
        builder.addParam("DATE", sdf.format(date));

        KakaoTalkService.requestSendMemo(new KakaoTalkMainActivity.KakaoTalkResponseCallback<Boolean>() {
                                             @Override
                                             public void onSuccess(Boolean result) {
                                                 Logger.d("send message to my chatroom : " + result);
                                             }
                                         }
                , "6019" // templateId
                , builder.build());
    }

    public static class KakaoTalkMainActivity extends Activity {

        public static class KakaoTalkMessageBuilder {
            public Map<String, String> messageParams = new HashMap<String, String>();

            public KakaoTalkMessageBuilder addParam(String key, String value) {
                messageParams.put("${" + key + "}", value);
                return this;
            }

            public Map<String, String> build() {
                return messageParams;
            }
        }



        private void redirectLoginActivity() {
            Intent intent = new Intent(this, KakaoTalkLoginActivity.class);
            startActivity(intent);
            finish();
        }

        private class KakaoTalkLoginActivity {
        }

        public abstract static class KakaoTalkResponseCallback<T> extends TalkResponseCallback<T> {


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
            }

            @Override
            public void onNotSignedUp() {
                redirectSignupActivity();
            }

            private void redirectSignupActivity() {
            }
        }
    }


}