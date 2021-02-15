package com.example.mute.stack;

import android.app.Service;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mute.R;
import com.example.mute.main.MainActivity;
import com.example.mute.main.Quest;

public class StackFragment extends Fragment {

    QuestView selected;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        MainActivity activity = (MainActivity) getActivity();
        LinearLayout stack = view.findViewById(R.id.todo_container);
        stack.removeAllViews();
        for (Quest quest : activity.getQuests()) {

            // reset timer
            if (quest.isPinned() && quest.getCycle() > 0 && (SystemClock.elapsedRealtime() - quest.getLastCheckpoint() > quest.getCycle()))
                quest.reset();
            QuestView questView = new QuestView(activity, quest);
            questView.setStrike(quest.getOffset() == 0);

            // set selection
            questView.setSelected(quest.isSelected());
            if (quest.isSelected()) selected = questView;

            // set pin listener
            questView.setPinnedListener(v -> questView.setPinned(!questView.getQuest().isPinned()));

            // set select listener
            questView.setOnClickListener(v -> {
                if (selected != null)
                    selected.setSelected(false);
                selected = questView;
                selected.setSelected(true);
            });

            // set settings portal
            questView.setOnLongClickListener(v -> {
                Vibrator vib = (Vibrator) activity.getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
                vib.vibrate(VibrationEffect.EFFECT_HEAVY_CLICK);
                activity.getIntent().putExtra("quest", quest);
                Navigation.findNavController(view).navigate(R.id.action_listFragment_to_settingsFragment);
                return true;
            });
            stack.addView(questView);
        }

        // portals
        stack.addView(new AddTriggerView(activity).setGlobalOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_listFragment_to_addingFragment)));
        view.findViewById(R.id.cover).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_listFragment_to_coverFragment));
        return view;
    }
}