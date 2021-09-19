package com.awareeTeam.awaree;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.List;

public class HomeworkDialog extends DialogFragment {
    public static final String TAG = "example_dialog";

    private Toolbar toolbar;
    private TextInputEditText homeworkTitle;
    private AutoCompleteTextView subjectType;
    private TextInputEditText dateDue;
    private TextInputEditText timeNeeded;
    private AutoCompleteTextView difficultySubject;
    private AutoCompleteTextView prioritySubject;
    private TextInputLayout homeworkTitleLayout;
    private TextInputLayout typeLayout;
    private TextInputLayout dateLayout;
    private TextInputLayout timeLayout;
    private TextInputLayout difficultyLayout;
    private TextInputLayout priorityLayout;
    private int counter;
    private int hour;
    private int minutes;

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
        dateDue = view.findViewById(R.id.dueDate);
        timeNeeded = view.findViewById(R.id.timeNeeded);
        difficultySubject = view.findViewById(R.id.difficultySubject);
        prioritySubject = view.findViewById(R.id.prioritySubject);
        toolbar = view.findViewById(R.id.toolbar);
        homeworkTitleLayout = view.findViewById(R.id.homeworkTitleLayout);
        typeLayout = view.findViewById(R.id.typeLayout);
        dateLayout = view.findViewById(R.id.dateLayout);
        timeLayout = view.findViewById(R.id.timeLayout);
        difficultyLayout = view.findViewById(R.id.difficultyLayout);
        priorityLayout = view.findViewById(R.id.priorityLayout);

        new FirebaseDatabaseHelper().readSubjects(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoadedSj(List<Subject> subjects, List<String> keys) {

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, keys);
                subjectType.setAdapter(adapter);
            }

            @Override
            public void DataIsLoadedHw(List<Homework> homework, List<String> keys) {

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        MaterialTimePicker.Builder materialTimeBuilder = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H);
        materialTimeBuilder.setTitleText("SELECT A TIME");
        final MaterialTimePicker materialTimePicker = materialTimeBuilder.build();


        dateDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getFragmentManager() , "MATERIAL_DATE_PICKER");
            }
        });
        timeNeeded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialTimePicker.show(getFragmentManager() , "MATERIAL_TIME_PICKER");
            }
        });
        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeS = null;
                hour = materialTimePicker.getHour();
                minutes = materialTimePicker.getMinute();
                if (minutes<10){
                    timeS = String.format("0%s", minutes);
                }else{
                    timeS = Integer.toString(minutes);
                }
                timeNeeded.setText(hour+":"+timeS);

            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        dateDue.setText(materialDatePicker.getHeaderText());
                        Log.d(TAG, "onPositiveButtonClick: "+ materialDatePicker.getSelection());
                    }
                });

        String[] difficulties = getResources().getStringArray(R.array.difficulties);
        ArrayAdapter<String> adapterDif = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, difficulties);
        difficultySubject.setAdapter(adapterDif);
        String[] priority = getResources().getStringArray(R.array.priority);
        ArrayAdapter<String> adapterPri = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, priority);
        prioritySubject.setAdapter(adapterPri);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle("Create a homework");
        toolbar.setTitleTextColor(getResources().getColor(R.color.Snow));
        toolbar.inflateMenu(R.menu.profile_settings);

        toolbar.setOnMenuItemClickListener(item -> {
            Homework homework = new Homework();
            counter = 0;

            Integer time = hour * 60 + minutes;
            //Log.d(TAG, "onViewCreated: "+time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");


            homework.setClassRef(subjectType.getText().toString());
            homework.setDifficulty(difficultySubject.getText().toString());
            homework.setPriority(prioritySubject.getText().toString());
            homework.setDuration(time.toString());
            //homework.setDueDate(dateFormat.format(dateDue.getText().toString()));

            if (TextUtils.isEmpty(homeworkTitle.getText())){
                homeworkTitleLayout.setError(getString(R.string.empty_error));
            } else { counter++; }
            if (TextUtils.isEmpty(subjectType.getText())){
                typeLayout.setError(getString(R.string.select_error));
            } else { counter++; }
            if (TextUtils.isEmpty(dateDue.getText())){
                dateLayout.setError(getString(R.string.select_error));
            } else { counter++; }
            if (TextUtils.isEmpty(timeNeeded.getText())){
                timeLayout.setError(getString(R.string.select_error));
            } else { counter++; }
            if (TextUtils.isEmpty(difficultySubject.getText())){
                difficultyLayout.setError(getString(R.string.select_error));
            } else { counter++; }
            if (TextUtils.isEmpty(prioritySubject.getText())){
                priorityLayout.setError(getString(R.string.select_error));
            } else { counter++; }

            if (counter == 6) {
                new FirebaseDatabaseHelper().addHomework(homework, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoadedSj(List<Subject> subjects, List<String> keys) {

                    }

                    @Override
                    public void DataIsLoadedHw(List<Homework> homeworks, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Snackbar.make(view, "Homework created (jk it's not implemented yet)", BaseTransientBottomBar.LENGTH_SHORT)
                                .show();
                        //Toast.makeText(getContext(), "Homework added", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
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
