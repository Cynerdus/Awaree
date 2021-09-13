package com.awareeTeam.awaree;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class HomeworkDialog extends DialogFragment {
    public static final String TAG = "example_dialog";

    private Toolbar toolbar;
    private TextInputLayout homeworkTitle;
    private AutoCompleteTextView subjectType;

    public static HomeworkDialog display(FragmentManager fragmentManager) {
        HomeworkDialog homeworkDialog = new HomeworkDialog();
        homeworkDialog.show(fragmentManager, TAG);
        return homeworkDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_homework, container, false);

        homeworkTitle = view.findViewById(R.id.homeworkTitle);
        subjectType = view.findViewById(R.id.subjectType);
        toolbar = view.findViewById(R.id.toolbar);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, SUBJECTS);
        subjectType.setAdapter(adapter);


        return view;
    }
    private static final String[] SUBJECTS = new String[] {
            "Mate", "Fizica", "Info", "Germany", "Spain"
    };


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle("Some Title");
        toolbar.inflateMenu(R.menu.profile_settings);
        toolbar.setOnMenuItemClickListener(item -> {
            dismiss();
            return true;
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

}
