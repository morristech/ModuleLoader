package com.example.slab.loader.logger;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hotstuNg on 2016/7/2.
 */
public class EventLogNode extends MessageOnlyLogFilter {
    @Override
    public void println(int priority, String tag, String msg, Throwable tr) {
        EventBus.getDefault().post(new EventMsg(priority, tag, msg, tr));
    }

}
