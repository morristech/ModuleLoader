/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.slab.loader.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.slab.loader.R;
import com.example.slab.loader.logger.EventLogNode;
import com.example.slab.loader.logger.Log;
import com.example.slab.loader.logger.LogFragment;
import com.example.slab.loader.logger.LogWrapper;
import com.example.slab.loader.logger.MessageOnlyLogFilter;


/**
 * Base launcher activity, to handle most of the common plumbing for samples.
 */
public class BaseLogActivity extends AppCompatActivity {

    public static final String TAG = "BaseLogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        initializeLogging();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem m = menu.add(Menu.NONE, 0, Menu.NONE, "Toggle log");
        m.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            toggleLogView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void toggleLogView() {
        Fragment f = getLogFragment();
        if (f == null) {
            return;
        }
        if (f.isHidden()) {
            getFragmentManager().beginTransaction().show(f).commitAllowingStateLoss();
        } else {
            getFragmentManager().beginTransaction().hide(f).commitAllowingStateLoss();
        }
    }

    protected LogFragment getLogFragment() {
        return (LogFragment) getFragmentManager().findFragmentById(R.id.id_logfragment);
    }

    /** Set up targets to receive log data */
    public void initializeLogging() {
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        // Wraps Android's native log framework
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);
        msgFilter.setNext(new EventLogNode());
        Log.i(TAG, "Ready");
    }
}
