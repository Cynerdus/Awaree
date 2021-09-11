package com.awareeTeam.awaree;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecyclerViewAdapterSj extends RecyclerView.Adapter<RecyclerViewAdapterSj.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private boolean dropped = false;

    private ArrayList<String> mSubjectNames = new ArrayList<>();
    private ArrayList<String> mSubjectCategory = new ArrayList<>();
    private ArrayList<Integer> mCredits = new ArrayList<>();
    private ArrayList<Integer> mCoursesNumber = new ArrayList<>();
    private ArrayList<Integer> mSeminariesNumber = new ArrayList<>();
    private ArrayList<Integer> mLabsNumber = new ArrayList<>();
    private ArrayList<Boolean> mIsExam = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterSj(Context mContext, ArrayList<String> mSubjectNames, ArrayList<String> mSubjectCategory, ArrayList<Integer> mCredits, ArrayList<Integer> mCoursesNumber, ArrayList<Integer> mSeminariesNumber, ArrayList<Integer> mLabsNumber, ArrayList<Boolean> mIsExam) {
        this.mSubjectNames = mSubjectNames;
        this.mSubjectCategory = mSubjectCategory;
        this.mCredits = mCredits;
        this.mCoursesNumber = mCoursesNumber;
        this.mSeminariesNumber = mSeminariesNumber;
        this.mLabsNumber = mLabsNumber;
        this.mIsExam = mIsExam;
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
        holder.dropDown1.setVisibility(View.GONE);
        holder.examType.setVisibility(View.GONE);
        holder.params.height = 256;
        dropped = false;

        holder.subjectName.setText(mSubjectNames.get(position));
        holder.subjectType.setText(mSubjectCategory.get(position));
        holder.credits.setText(mCredits.get(position).toString() + " CRD");
        if (mCoursesNumber.get(position) != 0){
            holder.coursesNumber.setText(mCoursesNumber.get(position).toString());
            holder.courses.setVisibility(View.VISIBLE);
        }
        if (mSeminariesNumber.get(position) != 0){
            holder.seminariesNumber.setText(mSeminariesNumber.get(position).toString());
            holder.seminaries.setVisibility(View.VISIBLE);
        }
        if (mLabsNumber.get(position) != 0){
            holder.labsNumber.setText(mLabsNumber.get(position).toString());
            holder.labs.setVisibility(View.VISIBLE);
        }
        if (mIsExam.get(position)) {
            holder.examType.setText(R.string.exam_true);
            holder.examType.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.NiceRed)));
        } else {
            holder.examType.setText(R.string.exam_false);
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: "+ mSubjectNames.get(position));
                if (dropped){
                    holder.dropDown1.setVisibility(View.GONE);
                    holder.examType.setVisibility(View.GONE);
                    holder.params.height = 256;
                    dropped = false;
                }else{
                    holder.dropDown1.setVisibility(View.VISIBLE);
                    holder.examType.setVisibility(View.VISIBLE);
                    holder.params.height = 768;
                    dropped = true;
                }

            }
        });
        holder.examType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "exam", Toast.LENGTH_SHORT).show();
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
        TextView labsNumber;
        TextView examType;
        LinearLayout dropDown1;
        LinearLayout courses;
        LinearLayout seminaries;
        LinearLayout labs;
        RelativeLayout parentLayout;
        ViewGroup.LayoutParams params;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subjectName);
            subjectType = itemView.findViewById(R.id.subjectType);
            credits = itemView.findViewById(R.id.creditsNumber);
            coursesNumber = itemView.findViewById(R.id.coursesNumber);
            seminariesNumber = itemView.findViewById(R.id.seminariesNumber);
            labsNumber = itemView.findViewById(R.id.labsNumber);
            examType = itemView.findViewById(R.id.examType);
            parentLayout = itemView.findViewById(R.id.parent_layout_sj);
            dropDown1 = itemView.findViewById(R.id.dropdown_element_1);
            courses = itemView.findViewById(R.id.courses);
            seminaries = itemView.findViewById(R.id.seminaries);
            labs = itemView.findViewById(R.id.labs);
            params = parentLayout.getLayoutParams();
        }
    }
}
