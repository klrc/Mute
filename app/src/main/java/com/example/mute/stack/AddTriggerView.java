package com.example.mute.stack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.mute.R;

@SuppressLint("ViewConstructor")
public class AddTriggerView extends LinearLayout {
    public AddTriggerView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_add, this, true);
    }

    public AddTriggerView setGlobalOnClickListener(@Nullable OnClickListener l) {
        setOnClickListener(l);
        return this;
    }
}
