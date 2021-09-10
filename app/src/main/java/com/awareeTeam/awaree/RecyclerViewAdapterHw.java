package com.awareeTeam.awaree;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RecyclerViewAdapterHw extends RecyclerView.Adapter<RecyclerViewAdapterHw.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

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

        holder.className.setText(mClassNames.get(position));
        holder.difficulty.setProgress(mDifficultyLvl.get(position));
        holder.difficultyLvl.setText("Difficulty: " + mDifficulty.get(position));
        holder.difficultyTime.setText(mDifficultyTime.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: "+ mClassNames.get(position));
                //Toast.makeText(mContext, mClassNames.get(position), Toast.LENGTH_SHORT).show();
                Snackbar.make(v.findViewById(R.id.coordinatorlayout), mClassNames.get(position),
                        Snackbar.LENGTH_SHORT)
                        .show();
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
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            difficulty = itemView.findViewById(R.id.difficulty);
            difficultyLvl = itemView.findViewById(R.id.difficultyLvl);
            difficultyTime = itemView.findViewById(R.id.difficultyTime);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
