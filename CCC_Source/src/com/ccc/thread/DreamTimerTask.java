package com.ccc.thread;

import java.util.Hashtable;
import java.util.Map;
import java.util.TimerTask;
import java.util.Map.Entry;

import com.ccc.dreamlog.LogDream;

/**
 * @author RedSword
 * @date 2013-05-19 10:49:54
 * @version 1.0
 */

public abstract class DreamTimerTask extends TimerTask {
    protected Map<String, Object> paramMap = new Hashtable<String, Object>();

    public DreamTimerTask(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
        LogDream.info(paramMap.get("Describe") + "  run...");
        for (Entry<String, Object> en : this.paramMap.entrySet()) {
            LogDream.info(en.getKey() + "#" + en.getValue() + "#");
        }
    }
}
