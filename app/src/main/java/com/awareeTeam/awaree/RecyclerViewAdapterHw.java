package com.awareeTeam.awaree;

import static android.content.ContentValues.TAG;
import static com.google.android.material.math.MathUtils.lerp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.math.MathUtils;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecyclerViewAdapterHw {
    private Context mContext;
    private HomeworkAdapter mHomeworkAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Homework> homework, List<String> keys) {
        mContext = context;
        mHomeworkAdapter = new HomeworkAdapter(homework, keys);
        mHomeworkAdapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new HomeworkAdapter(homework, keys));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    class HomeworkItemView extends RecyclerView.ViewHolder{
        TextView className;
        ProgressBar difficulty;
        TextView difficultyLvl;
        TextView difficultyTime;
        Chip chip_alarm, chip_calendar, chip_done, chip_timer;
        RelativeLayout parentLayout;
        LinearLayout dropDown;
        ViewGroup.LayoutParams params;
        ChipGroup chipGroup;
        Chip chipLow, chipMed, chipHigh;
        private boolean dropped;
        private String key;

        public HomeworkItemView(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.layout_homework, parent, false));

            className = itemView.findViewById(R.id.className);
            difficulty = itemView.findViewById(R.id.difficulty);
            difficultyLvl = itemView.findViewById(R.id.difficultyLvl);
            difficultyTime = itemView.findViewById(R.id.difficultyTime);
            dropDown = itemView.findViewById(R.id.dropdown_element_2);
            parentLayout = itemView.findViewById(R.id.parent_layout_hw);
            chip_alarm = itemView.findViewById(R.id.chip_alarm);
            chip_calendar = itemView.findViewById(R.id.chip_calendar);
            chip_done = itemView.findViewById(R.id.chip_done);
            chip_timer = itemView.findViewById(R.id.chip_timer);
            params = parentLayout.getLayoutParams();
            chipGroup = itemView.findViewById(R.id.priority_chip);
            chipLow = itemView.findViewById(R.id.chip_low);
            chipMed = itemView.findViewById(R.id.chip_medium);
            chipHigh = itemView.findViewById(R.id.chip_high);
        }
        public void bind(Homework homework, String key){
            Log.d(TAG, "onBindViewHolder: called.");
            dropDown.setVisibility(View.GONE);
            params.height = 370;
            dropped = false;

            int time = Integer.parseInt(homework.getDuration());
            int color;
            int green = R.color.Persian_Green;
            int yellow = R.color.Orange_Yellow_Crayola;
            int red = R.color.NiceRed;

            className.setText(homework.getClassRef());
            difficultyLvl.setText("Difficulty: " + homework.getDifficulty());
            if (time <= 30){
                color = green;
            } else if (time <= 60){
                color = yellow;
            } else {
                color = red;
            }
            int progress = (int) lerp(10, 120, time)/100;
            Log.d(TAG, "onBindViewHolder: " + progress);
            difficulty.setProgressTintList(AppCompatResources.getColorStateList(mContext, color));
            //difficultyTime.setTextColor(AppCompatResources.getColorStateList(mContext, color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                difficulty.setProgress(progress, true);
            }
            difficultyTime.setText(homework.getDuration() + " minutes");
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: clicked on: " + homework.getClassRef());
                    Log.d(TAG, "onClick: dropped is " + dropped);
                    if (dropped){
                        dropDown.setVisibility(View.GONE);
                        params.height = 370;
                        dropped = false;
                    }else{
                        dropDown.setVisibility(View.VISIBLE);
                        params.height = 900;
                        dropped = true;
                    }
                }
            });
            MaterialTimePicker.Builder materialTimeBuilder = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H);
            materialTimeBuilder.setTitleText("SELECT A TIME");
            final MaterialTimePicker materialTimePicker = materialTimeBuilder.build();
            chip_alarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //materialTimePicker.show(materialTimePicker.getParentFragmentManager(), "MATERIAL_TIME_PICKER");
                }
            });
            materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE, homework.getClassRef());
                    intent.putExtra(AlarmClock.EXTRA_HOUR, materialTimePicker.getHour());
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, materialTimePicker.getMinute());

                    if(intent.resolveActivity(mContext.getPackageManager()) != null){
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "You don't have a clock app", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            chip_calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, homework.getClassRef());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, homework.getDifficulty());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "POLITEHNICA University of Bucharest");
                    //intent.putExtra(CalendarContract.Events.LAST_DATE, homework.getDifficulty());

                    if(intent.resolveActivity(mContext.getPackageManager()) != null){
                        mContext.startActivity(intent);
                    }else{
                        Toast.makeText(mContext, "You don't have a calendar app", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            chip_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new FirebaseDatabaseHelper().deleteHomework(key, new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoadedSj(List<Subject> subjects, List<String> keys) {

                        }

                        @Override
                        public void DataIsLoadedHw(List<Homework> homeworks, List<String> keys) {

                        }

                        @Override
                        public void DataIsInserted() {

                        }

                        @Override
                        public void DataIsUpdated() {

                        }

                        @Override
                        public void DataIsDeleted() {
                            //CoordinatorLayout coordinatorLayout = itemView.findViewById(R.id.coordinatorlayout);
                            /*Snackbar.make(view, "Homework created (jk it's not implemented yet)", BaseTransientBottomBar.LENGTH_SHORT)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .show();*/
                        }
                    });
                }
            });
            chip_timer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TimerActivity.class);
                    intent.putExtra("time", homework.getDuration());
                    intent.putExtra("subject", homework.getClassRef());
                    intent.putExtra("priority", homework.getPriority());
                    intent.putExtra("difficulty", homework.getDifficulty());
                    intent.putExtra("key", key);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                            Pair.create(difficultyTime, "time"));
                    mContext.startActivity(intent, options.toBundle());
                }
            });
            if (chipHigh.getText().equals(homework.getPriority())){
                chipHigh.setSelected(true);
            } else if (chipMed.getText().equals(homework.getPriority())){
                chipMed.setSelected(true);
            } else {
                chipLow.setSelected(true);
            }

            chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(ChipGroup group, int checkedId) {
                    Chip chip = chipGroup.findViewById(checkedId);
                    if (chip != null) {
                        Toast.makeText(mContext, "Chip is " + chip.getText(), Toast.LENGTH_SHORT).show();
                        Homework homework1 = new Homework();
                        homework1.setDueDate(homework.getDueDate());
                        homework1.setClassRef(homework.getClassRef());
                        homework1.setDuration(homework.getDuration());
                        homework1.setPriority(chip.getText().toString());
                        homework1.setDifficulty(homework.getDifficulty());
                        new FirebaseDatabaseHelper().updateHomework(key, homework1, new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoadedSj(List<Subject> subjects, List<String> keys) {

                            }

                            @Override
                            public void DataIsLoadedHw(List<Homework> homeworks, List<String> keys) {

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
                    }
                }
            });

            this.key = key;
        }
    }
    class HomeworkAdapter extends RecyclerView.Adapter<HomeworkItemView>{
        private List<Homework> mHomeworkList;
        private List<String> mKeys;

        public HomeworkAdapter(List<Homework> mHomeworkList, List<String> mKeys) {
            this.mHomeworkList = mHomeworkList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public HomeworkItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HomeworkItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeworkItemView holder, int position) {
            holder.bind(mHomeworkList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mHomeworkList.size();
        }
    }
}