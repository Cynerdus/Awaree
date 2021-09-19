package com.awareeTeam.awaree;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceSubjects;
    private DatabaseReference mReferenceHomework;
    private List<Subject> subjects = new ArrayList<>();
    private List<Homework> homeworks = new ArrayList<>();
    private String user;
    private String currentYear;
    private String currentSemester;
    private String profile;

    public interface DataStatus{
        void DataIsLoadedSj(List<Subject> subjects, List<String> keys);
        void DataIsLoadedHw(List<Homework> homeworks, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        profile = "CTI"; // TODO link users profile and semester/year when he logs in
        currentYear = "Anul_1";
        currentSemester = "Semestrul_1";
        user = "https://awaree-ea116-default-rtdb.firebaseio.com/Specializations/"+profile+"/"+currentYear+"/"+currentSemester;
        mReferenceSubjects = mDatabase.getReferenceFromUrl(user);
        mReferenceHomework = mDatabase.getReference("HwTemp"); // TODO set a unique file for different users
    }

    public void readSubjects(final DataStatus dataStatus){
        mReferenceSubjects.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subjects.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Log.d(TAG, "onDataChange: " + keyNode.getKey());
                    Subject subject = keyNode.getValue(Subject.class);
                    subjects.add(subject);
                }
                dataStatus.DataIsLoadedSj(subjects, keys);
                Log.d(TAG, "onDataChange: data is loaded sj");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }
    public void readHomework(final DataStatus dataStatus){
        mReferenceHomework.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                homeworks.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Log.d(TAG, "onDataChange: " + keyNode.getKey());
                    Homework homework = keyNode.getValue(Homework.class);
                    homeworks.add(homework);
                }
                dataStatus.DataIsLoadedHw(homeworks, keys);
                Log.d(TAG, "onDataChange: data is loaded hw");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addHomework(Homework homework, final DataStatus dataStatus){
        String key = mReferenceHomework.push().getKey();
        mReferenceHomework.child(key).setValue(homework).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void updateHomework(String key, Homework homework, final DataStatus dataStatus){
        mReferenceHomework.child(key).setValue(homework).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteHomework(String key,final DataStatus dataStatus){
        mReferenceHomework.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dataStatus.DataIsDeleted();
            }
        });
    }

}
