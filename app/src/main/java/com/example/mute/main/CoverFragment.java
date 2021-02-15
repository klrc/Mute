package com.example.mute.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mute.R;

public class CoverFragment extends Fragment {

    ChronometerTimer timer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cover, container, false);

        View pause = view.findViewById(R.id.pause);
        pause.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_coverFragment_to_listFragment));

        MainActivity activity = (MainActivity) requireActivity();
        Quest selectedQuest = activity.getSelectedQuest();
        timer = new ChronometerTimer(view.findViewById(R.id.timer), selectedQuest, pause);

        if (selectedQuest == null) {
            timer.setTypeface(Typeface.SANS_SERIF);
            timer.setText("暂无标记, 点击选择");
            timer.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_coverFragment_to_listFragment));
        } else {
            Typeface typeFace = Typeface.createFromAsset(activity.getAssets(), "roboto-mono-v13-latin-100.ttf");
            timer.setTypeface(typeFace);
            timer.reset();
            timer.setOnClickListener(v -> {
                if (timer.isActivated()) timer.stop();
                else timer.start(activity);
            });
        }
        return view;
    }
}