package com.awareeTeam.awaree;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class RecyclerViewAdapterHw extends RecyclerView.Adapter<RecyclerViewAdapterHw.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private boolean isScrollEnabled = true;
    private boolean dropped = false;

    private ArrayList<String> mClassNames = new ArrayList<>();
    private ArrayList<String> mDifficulty = new ArrayList<>();
    private ArrayList<Integer> mDifficultyLvl = new ArrayList<>();
    private ArrayList<String> mDifficultyTime = new ArrayList<>();
    private Context mContext;


    public RecyclerViewAdapterHw(Context mContext, ArrayList<String> mClassNames, ArrayList<String> mDifficulty, ArrayList<Integer> mDifficultyLvl, ArrayList<String> mDifficultyTime) {
        this.mClassNames = mClassNames;
        this.mDifficulty = mDifficulty;
        this.mDifficultyLvl = mDifficultyLvl;
        this.mDifficultyTime = mDifficultyTime;
        this.mContext = mContext;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_homework, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.params.height = 420;

        holder.className.setText(mClassNames.get(position));
        holder.difficulty.setProgress(mDifficultyLvl.get(position));
        holder.difficultyLvl.setText("Difficulty: " + mDifficulty.get(position));
        holder.difficultyTime.setText(mDifficultyTime.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mClassNames.get(position));
                if (dropped){
                    holder.dropDown.setVisibility(View.GONE);
                    holder.params.height = 420;
                    dropped = false;
                }else{
                    holder.dropDown.setVisibility(View.VISIBLE);
                    holder.params.height = 896;
                    dropped = true;
                }
                //holder.params.height = 768;
                //holder.parentLayout.setLayoutParams(new RelativeLayout.LayoutParams(holder.params));
            }

        });
        holder.chip_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Alarm", Toast.LENGTH_SHORT).show();
            }
        });
        holder.chip_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Calendar", Toast.LENGTH_SHORT).show();
            }
        });
        holder.chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = holder.chipGroup.findViewById(checkedId);
                if (chip != null){
                    Toast.makeText(mContext, "Chip is " + chip.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mClassNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView className;
        ProgressBar difficulty;
        TextView difficultyLvl;
        TextView difficultyTime;
        Chip chip_alarm;
        Chip chip_calendar;
        RelativeLayout parentLayout;
        LinearLayout dropDown;
        ViewGroup.LayoutParams params;
        ChipGroup chipGroup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            difficulty = itemView.findViewById(R.id.difficulty);
            difficultyLvl = itemView.findViewById(R.id.difficultyLvl);
            difficultyTime = itemView.findViewById(R.id.difficultyTime);
            dropDown = itemView.findViewById(R.id.dropdown_element_2);
            parentLayout = itemView.findViewById(R.id.parent_layout_hw);
            chip_alarm = itemView.findViewById(R.id.chip_alarm);
            chip_calendar = itemView.findViewById(R.id.chip_calendar);
            params = parentLayout.getLayoutParams();
            chipGroup = itemView.findViewById(R.id.priority_chip);
        }
    }
}
