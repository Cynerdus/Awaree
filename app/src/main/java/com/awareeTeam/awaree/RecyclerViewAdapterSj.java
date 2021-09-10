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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RecyclerViewAdapterSj extends RecyclerView.Adapter<RecyclerViewAdapterSj.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mSubjectNames = new ArrayList<>();
    private ArrayList<String> mSubjectCategory = new ArrayList<>();
    private ArrayList<Integer> mCredits = new ArrayList<>();
    private ArrayList<Integer> mCoursesNumber = new ArrayList<>();
    private ArrayList<Integer> mSeminariesNumber = new ArrayList<>();
    private ArrayList<Integer> mLabsNumber = new ArrayList<>();
    private ArrayList<Boolean> isExam = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterSj(Context mContext, ArrayList<String> mSubjectNames, ArrayList<String> mSubjectCategory, ArrayList<Integer> mCredits, ArrayList<Integer> mCoursesNumber, ArrayList<Integer> mSeminariesNumber, ArrayList<Integer> mLabsNumber, ArrayList<Boolean> isExam) {
        this.mSubjectNames = mSubjectNames;
        this.mSubjectCategory = mSubjectCategory;
        this.mCredits = mCredits;
        this.mCoursesNumber = mCoursesNumber;
        this.mSeminariesNumber = mSeminariesNumber;
        this.mLabsNumber = mLabsNumber;
        this.isExam = isExam;
        this.mContext = mContext;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_subject, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.subjectName.setText(mSubjectNames.get(position));
        holder.subjectType.setText(mSubjectCategory.get(position));
        holder.credits.setText(mCredits.get(position).toString() + " CRD");
        //holder.coursesNumber.setText(mCoursesNumber.get(position).toString());
        //holder.seminariesNumber.setText(mSeminariesNumber.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: "+ mSubjectNames.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return mSubjectNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView subjectName;
        TextView subjectType;
        TextView credits;
        TextView coursesNumber;
        TextView seminariesNumber;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subjectName);
            subjectType = itemView.findViewById(R.id.subjectType);
            credits = itemView.findViewById(R.id.creditsNumber);
            //coursesNumber = itemView.findViewById(R.id.difficultyLvl);
            //seminariesNumber = itemView.findViewById(R.id.difficultyTime);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
