package com.example.jennifer.feedbackpupils;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Models.Course;

public class Subscribe extends AppCompatActivity {

    static String TAG = "Subscribe";
    //Layout variable
    private EditText mName;
    private EditText mCode;
    private Button mSend ;
    private TextView courseError;
    private RecyclerView mRecyclerView;

    //Firebase Reference
    private DatabaseReference mCourseref;
    private DatabaseReference mCoursesRef;
    private String found;


    private ArrayList<Course> availableCourses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        // Inflate the layout for this fragment
        mCourseref = FirebaseDatabase.getInstance().getReference();
        mCoursesRef = mCourseref.child("courses");
        mCode = (EditText) findViewById(R.id.courseCode);
        mSend = (Button) findViewById(R.id.subscribe);
        courseError = (TextView) findViewById(R.id.error);
        found = null;

        //mSend.setEnabled(false);

        mSend.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = mCode.getText().toString();
                switch(v.getId()){
                    case R.id.subscribe:
                        if(!isEmpty(mCode)){
                            if((myCourse(code))==true /*equals(code)*/){

                                // add course in main
                                courseError.setText("Found!");
                            }
                            //Couldn't find the course in the database
                            else {
                                courseError.setText("Couldn't find the course!");}
                        }

                        //Field is empty
                        else{
                            courseError.setText("The field is empty!");

                        }

                }

            }

        });

    }



    /*
   *
   * Check if the field is empty
   *
   * @param    the field that will be checked
   * @return   false if the field is empty
   */
    private boolean isEmpty(EditText textInputEditText) {
        if (textInputEditText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }


    /*
    *
    * Searching for a course in the database
    *
    * @param    a course code that will be searched in the database
    * @return   true if the course is found in the database
    */
    private boolean myCourse(String courseCode) {

        // afg324
        final String courseID = courseCode;
        Log.i(TAG, "myCoursemetod!");
        //int count = availableCourses.size();

        mCoursesRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //A list of all children for coursers
                //Log.i("Count " ,""+dataSnapshot.getChildrenCount());
                Iterable<DataSnapshot> courses = dataSnapshot.getChildren();

                for (DataSnapshot course : courses){

                    if(course.child("code").getValue().equals(courseID)){
                        Course c = course.getValue(Course.class);
                    }



                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        Log.i(":D " ,""+found);

        return true;


    }

    public ArrayList<Course> getAvailableCourses() {
        return availableCourses;
    }

    public void setAvailableCourses(ArrayList<Course> availableCourses) {
        this.availableCourses = availableCourses;
    }


}