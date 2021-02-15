package com.example.mute.main;

import android.os.SystemClock;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.mute.stack.QuestView.time2text;

public class Quest implements Serializable {

    private final String name;
    private final long cycle;
    private final long base;
    private final long initOffset;
    private boolean pinned;
    private boolean reversed;
    private long offset;
    private boolean selected;
    private long lastCheckpoint;
    private final String createDateString;

    public Quest(String name, long offset, long cycle) {
        this(name, false, offset, cycle, 0L, offset);
    }

    public Quest(String name, boolean reversed, long offset, long cycle, long base, long initOffset) {
        this.name = name;
        this.pinned = (cycle > 0);
        this.reversed = reversed;
        this.offset = offset;
        this.cycle = cycle;
        this.base = base + offset;
        this.initOffset = initOffset;
        this.selected = false;
        this.createDateString = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
        resetLastCheckpoint();
    }

    public void resetLastCheckpoint() {
        this.lastCheckpoint = SystemClock.elapsedRealtime();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        String description = "";
        description += (cycle > 0) ? "每隔 " + time2text(cycle) + " 刷新" : "不再刷新";
        description += (reversed) ? ", 反转计时" : "";
        return description;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getOffset() {
        return offset;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getCreateDate() {
        return createDateString;
    }

    public long getCycle() {
        return cycle;
    }

    public long getTotalDuration() {
        return base - offset;
    }

    public void reset() {
        setOffset(initOffset);
    }

    public long getLastCheckpoint() {
        return lastCheckpoint;
    }
}
