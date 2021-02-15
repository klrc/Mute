package com.example.mute.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mute.R;
import com.example.mute.main.MainActivity;
import com.example.mute.main.Quest;
import com.google.android.material.switchmaterial.SwitchMaterial;

import static com.example.mute.stack.QuestView.time2text;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        MainActivity activity = (MainActivity) getActivity();
        Quest quest = (Quest) activity.getIntent().getSerializableExtra("quest");
        ((TextView) view.findViewById(R.id.settings_title)).setText(quest.getName());
        ((TextView) view.findViewById(R.id.settings_create_date)).setText(quest.getCreateDate());
        ((TextView) view.findViewById(R.id.settings_recurse)).setText(time2text(quest.getCycle()));
        ((TextView) view.findViewById(R.id.settings_total)).setText(time2text(quest.getTotalDuration()));
        SwitchMaterial switchMaterial = view.findViewById(R.id.settings_reverse_switch);
        switchMaterial.setChecked(quest.isReversed());
        switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> quest.setReversed(isChecked));
        view.findViewById(R.id.settings_reset).setOnClickListener(v -> {
            quest.reset();
            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_listFragment);
        });
        view.findViewById(R.id.settings_remove).setOnClickListener(v -> {
            activity.removeQuest(quest);
            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_listFragment);
        });
        view.findViewById(R.id.exit).setOnClickListener(v -> activity.onBackPressed());
        return view;
    }
}