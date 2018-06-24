package com.shiger.pingworld;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {


    private String TAG = this.getClass().getSimpleName();

    //
    String[] mPingArray = {"www.baidu.com"};
    //
    TextView mPingBaiduKey, mPingBaiduTv;
    Button mButtonBaidu;
    //
    private final int MSG_PING_START_Baidu = 0;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_PING_START_Baidu:
                    PingUtils pingUtils = new PingUtils();
                    String pingResult = pingUtils.isPingSuccess(1,mPingArray[0]);
                    Log.d(TAG, "onCreate: pingResult = " + pingResult);
                    String pingAverage = pingUtils.extractNumAverage(pingResult);
                    mPingBaiduKey.setText(mPingArray[0]);
                    mPingBaiduTv.setText(pingAverage);

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        setupView();

        //init
        mHandler.sendEmptyMessage(MSG_PING_START_Baidu);


    }


    private void setupView(){
        mPingBaiduKey = findViewById(R.id.pingBaiduKey);
        mPingBaiduTv = findViewById(R.id.pingBaidu);
        mButtonBaidu = findViewById(R.id.refrashBaidu);
        mButtonBaidu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.refrashBaidu:
                mHandler.sendEmptyMessage(MSG_PING_START_Baidu);
                break;
        }
    }
}
