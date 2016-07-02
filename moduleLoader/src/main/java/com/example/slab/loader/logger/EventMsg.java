package com.example.slab.loader.logger;

/**
 * Created by hotstuNg on 2016/7/2.
 */
public class EventMsg {
    public final int priority;
    public final String tag;
    public final String msg;
    public final Throwable tr;

    public EventMsg(int priority, String tag, String msg, Throwable tr) {
        this.priority = priority;
        this.tag = tag;
        this.msg = msg;
        this.tr = tr;
    }
}
