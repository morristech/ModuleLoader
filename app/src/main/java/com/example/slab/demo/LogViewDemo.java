package com.example.slab.demo;

import android.os.Bundle;
import android.view.View;

import com.example.slab.loader.activities.BaseLogActivity;
import com.example.slab.loader.logger.Log;

/**
 * Created by hotstuNg on 2016/7/2.
 */
public class LogViewDemo extends BaseLogActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_logviewdemo);
    }

    public void sendmsg(View view) {
        Log.d("LogViewDemo", "sendmsg" + System.currentTimeMillis());
    }
}
