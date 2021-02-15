package com.example.mute.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

import com.example.mute.main.MainActivity;
import com.example.mute.main.Quest;

public class ChronometerTimer {

    private final View pause;
    private boolean activated;
    private final Quest quest;
    private final Chronometer view;

    public ChronometerTimer(Chronometer view, Quest quest, View pause) {
        this.view = view;
        this.quest = quest;
        this.pause = pause;
        setActivated(false);
    }

    void setActivated(boolean activated) {
        pause.setVisibility(activated ? View.GONE : View.VISIBLE);
        view.setTextColor(activated ? Color.WHITE : Color.GRAY);
        this.activated = activated;
    }

    public void setText(String text) {
        view.setText(text);
    }

    public void setOnClickListener(View.OnClickListener l) {
        view.setOnClickListener(l);
    }

    public void reset() {
        view.setTextSize(96);
        view.setFormat("%s");
        if (quest.isReversed()) {
            view.setBase(SystemClock.elapsedRealtime() - quest.getOffset());
        } else {
            view.setBase(SystemClock.elapsedRealtime() + quest.getOffset());
        }
        view.setCountDown(!quest.isReversed());
    }

    @SuppressLint("SetTextI18n")
    public void start(MainActivity activity) {
        reset();
        view.start();
        view.setOnChronometerTickListener(chronometer -> {
            if (!quest.isReversed()) {
                if (view.getBase() - SystemClock.elapsedRealtime() <= 0) {
                    view.setText("00:00");
                    if (!quest.isPinned()) {
                        activity.removeQuest(quest);
                    }
                    stop();
                }
            }
        });
        setActivated(true);
    }

    public void stop() {
        view.stop();
        if (quest.isReversed()) {
            long offset = SystemClock.elapsedRealtime() - view.getBase();
            quest.setOffset(offset);
        } else {
            long offset = view.getBase() - SystemClock.elapsedRealtime();
            quest.setOffset(offset < 0 ? 0 : offset);
        }
        setActivated(false);
    }

    public boolean isActivated() {
        return activated;
    }


    public void setTypeface(Typeface tf) {
        view.setTypeface(tf);
    }
}
