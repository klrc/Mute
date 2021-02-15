package com.example.mute.addNew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mute.R;
import com.example.mute.main.MainActivity;
import com.example.mute.main.Quest;

public class AddingFragment extends Fragment {

    MainActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding, container, false);

        activity = (MainActivity) getActivity();
        EditText e1 = view.findViewById(R.id.add_title);
        View e2 = view.findViewById(R.id.add_offset);
        View e3 = view.findViewById(R.id.add_cycle);
        ((TextView) e3.findViewById(R.id.input_minute)).setText("0");
        view.findViewById(R.id.submit).setOnClickListener(v -> submit(e1, e2, e3, view));
        return view;
    }

    private void submit(EditText e1, View e2, View e3, View view) {
        String title = e1.getText().toString();
        long offset = getTimeOffset(e2);
        long cycle = getTimeOffset(e3);
        activity.addQuest(new Quest(title, offset, cycle));
        Navigation.findNavController(view).navigate(R.id.action_addingFragment_to_listFragment);
    }

    private long getTimeOffset(View v) {
        long hour = Long.parseLong(((EditText) v.findViewById(R.id.input_hour)).getText().toString());
        long min = Long.parseLong(((EditText) v.findViewById(R.id.input_minute)).getText().toString());
        long sec = Long.parseLong(((EditText) v.findViewById(R.id.input_second)).getText().toString());
        return ((hour * 60 + min) * 60 + sec) * 1000;
    }
}