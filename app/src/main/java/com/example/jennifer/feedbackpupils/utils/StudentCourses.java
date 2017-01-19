package com.example.jennifer.feedbackpupils.utils;

import com.example.jennifer.feedbackpupils.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class gets the courses that a student has subscribed to
 *
 * Created by matej on 2017-01-18.
 */


public class StudentCourses {

    private ArrayList<String> mStudentCourses = new ArrayList<>();
    private String mStudentName;
    private DatabaseReference mDataRef;
    private DatabaseReference mStudentsRef;


    // Class constructor, right now is hardcoded. When login is done
    // the constructor will take a String username parameter
    private StudentCourses(){

        mStudentName = "Jennifer";

        mDataRef = FirebaseDatabase.getInstance().getReference();
        mStudentsRef = mDataRef.child("users").child("students");

        // query that gets database reference to a user which username is "Jennifer" (mStudentName)
        Query getStudentRef = mStudentsRef.orderByChild("username").equalTo(mStudentName);

        // put an event listener to that specific node
        getStudentRef.addValueEventListener(new StudentCoursesChildEventListener());



    }


    private class StudentCoursesChildEventListener implements ValueEventListener {

        private void add(DataSnapshot dataSnapshot) {
            Student student = dataSnapshot.getValue(Student.class);
            //TODO Set database key for student

            // saves subscribedto node to a temporary map
            Map<String, Boolean> temp = student.getSubscribedto();
            mStudentCourses = null;
            // get all the keys(courses) from the map and save it to an arraylist
            for (String course : temp.keySet()) {
                mStudentCourses.add(course);
            }

        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            add(dataSnapshot);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    public ArrayList<String> getStudentCourses() {
        return mStudentCourses;
    }

    public void setStudentCourses(ArrayList<String> mStudentCourses) {
        this.mStudentCourses = mStudentCourses;
    }
}
