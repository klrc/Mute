package com.example.mute.stack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.mute.R;
import com.example.mute.main.Quest;

@SuppressLint("ViewConstructor")
public class QuestView extends LinearLayout {

    private final Quest quest;
    View doneView;
    TextView nameView;
    TextView descriptionView;
    TextView timeView;
    ImageView pinnedView;

    public QuestView(Context context, Quest quest) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_todo, this, true);
        nameView = findViewById(R.id.todo_info_name);
        descriptionView = findViewById(R.id.todo_info_description);
        timeView = findViewById(R.id.todo_time);
        pinnedView = findViewById(R.id.todo_is_pinned);
        doneView = findViewById(R.id.todo_done);
        this.quest = quest;
        setName(quest.getName());
        setDescription(quest.getDescription());
        setTime(quest.getOffset());
        setPinned(quest.isPinned());
    }

    public void setStrike(boolean strike) {
        if (strike) {
            nameView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            nameView.setTextColor(Color.GRAY);
            descriptionView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            descriptionView.setTextColor(Color.GRAY);
            timeView.setTextColor(Color.GRAY);
            doneView.setVisibility(VISIBLE);
        }
    }

    public void setSelected(boolean selected) {
        quest.setSelected(selected);
        if (selected) {
            Drawable background = ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_rect, null);
            setBackground(background);
        } else {
            setBackground(null);
        }
    }

    public void setName(String text) {
        nameView.setText(text);
    }

    public void setDescription(String text) {
        descriptionView.setText(text);
    }

    public void setTime(long time) {
        timeView.setText(time2text(time));
    }

    public static String time2text(long time) {
        int sec = (int) (time / 1000);
        int min = sec / 60;
        int hour = min / 60;
        String text;
        if (hour > 0) {
            text = hour + "h" + min % 60 + "m";
        } else if (min > 0) {
            text = min + "m" + sec % 60 + "s";
        } else {
            text = sec + "s";
        }
        return text;
    }

    public void setPinned(boolean pinned) {
        quest.setPinned(pinned);
        int color = pinned ? R.color.orange : R.color.white;
        pinnedView.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(color)));
    }

    public Quest getQuest() {
        return quest;
    }

    public void setPinnedListener(OnClickListener l) {
        findViewById(R.id.todo_is_pinned_wrapper).setOnClickListener(l);
    }
}
