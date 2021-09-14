package com.awareeTeam.awaree;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


public class RecyclerViewAdapterSj{
    private Context mContext;
    private SubjectsAdapter mSubjectsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Subject> subject, List<String> keys){
        mContext = context;
        mSubjectsAdapter = new SubjectsAdapter(subject, keys);
        mSubjectsAdapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new SubjectsAdapter(subject, keys));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    class SubjectsItemView extends RecyclerView.ViewHolder{

        private TextView subjectName;
        private TextView subjectType;
        private TextView credits;
        private TextView coursesNumber;
        private TextView seminariesNumber;
        private TextView labsNumber;
        private TextView examType;
        private LinearLayout dropDown1;
        private LinearLayout courses;
        private LinearLayout seminaries;
        private LinearLayout labs;
        private RelativeLayout parentLayout;
        private ViewGroup.LayoutParams params;
        private boolean dropped;
        private String key;


        public SubjectsItemView(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.layout_subject, parent, false));

            subjectName = itemView.findViewById(R.id.subjectName);
            subjectType = itemView.findViewById(R.id.subjectType);
            credits = itemView.findViewById(R.id.creditsNumber);
            coursesNumber = itemView.findViewById(R.id.coursesNumber);
            seminariesNumber = itemView.findViewById(R.id.seminariesNumber);
            labsNumber = itemView.findViewById(R.id.labsNumber);
            examType = itemView.findViewById(R.id.examType);
            dropDown1 = itemView.findViewById(R.id.dropdown_element_1);
            courses = itemView.findViewById(R.id.courses);
            seminaries = itemView.findViewById(R.id.seminaries);
            labs = itemView.findViewById(R.id.labs);
            parentLayout = itemView.findViewById(R.id.parent_layout_sj);
            params = parentLayout.getLayoutParams();
        }
        public void bind(Subject subject, String key){
            dropDown1.setVisibility(View.GONE);
            examType.setVisibility(View.GONE);
            params.height = 280;
            dropped = false;

            subjectName.setText(subject.getSubjectName());
            subjectType.setText(subject.getSubjectCategory());
            credits.setText(subject.getCredits() + " CRD");
            if (!subject.getCoursesNumber().equals("0")) {
                coursesNumber.setText(subject.getCoursesNumber());
                courses.setVisibility(View.VISIBLE);
            }
            if (!subject.getSeminariesNumber().equals("0")){
                seminariesNumber.setText(subject.getSeminariesNumber());
                seminaries.setVisibility(View.VISIBLE);
            }
            if (!subject.getLabsNumber().equals("0")){
                labsNumber.setText(subject.getLabsNumber());
                labs.setVisibility(View.VISIBLE);
            }
            if (subject.getIsExam().equals("true")) {
                examType.setText(R.string.exam_true);
                examType.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.NiceRed)));
            } else {
                examType.setText(R.string.exam_false);
            }

            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dropped){
                        dropDown1.setVisibility(View.GONE);
                        examType.setVisibility(View.GONE);
                        params.height = 280;
                        dropped = false;
                    }else{
                        dropDown1.setVisibility(View.VISIBLE);
                        examType.setVisibility(View.VISIBLE);
                        params.height = 768;
                        dropped = true;
                    }

                }
            });
            examType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "exam", Toast.LENGTH_SHORT).show();
                }
            });
            this.key = key;
        }
    }
    class SubjectsAdapter extends RecyclerView.Adapter<SubjectsItemView>{
        private List<Subject> mSubjectList;
        private List<String> mKeys;

        public SubjectsAdapter(List<Subject> mSubjectList, List<String> mKeys) {
            this.mSubjectList = mSubjectList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public SubjectsItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SubjectsItemView(parent);
        }

        @Override
        public long getItemId(int position) {
            if (position < mSubjectList.size()){
                mSubjectList.get(position).getSubjectName();
            }
            return RecyclerView.NO_ID;
        }

        @Override
        public void onBindViewHolder(@NonNull SubjectsItemView holder, int position) {
            holder.bind(mSubjectList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mSubjectList.size();
        }
    }
}
