package com.awareeTeam.awaree;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceSubjects;
    private List<Subject> subjects = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Subject> subjects, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceSubjects = mDatabase.getReference("Profiles");
    }

    public void readSubjects(final DataStatus dataStatus){
        mReferenceSubjects.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subjects.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Subject subject = keyNode.getValue(Subject.class);
                    subjects.add(subjects);
                }
                dataStatus.DataIsLoaded(subjects, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })
    }
}
