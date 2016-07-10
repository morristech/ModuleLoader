package com.example.slab.demo;

import android.os.Bundle;
import android.view.View;

import com.example.slab.loader.activities.SampleActivityBase;
import com.example.slab.loader.logger.Log;
import com.example.slab.loader.logger.LogFragment;

/**
 * Created by hotstuNg on 2016/7/2.
 */
public class LogViewDemo extends SampleActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_logviewdemo);
    }

    @Override
    protected LogFragment getLogFragment() {
        return (LogFragment) getFragmentManager().findFragmentById(R.id.log_fragment);
    }

    public void sendmsg(View view) {
        Log.d("LogViewDemo", "sendmsg" + System.currentTimeMillis());
    }
}
