package com.example.jennifer.feedbackpupils.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jennifer.feedbackpupils.R;
import com.example.jennifer.feedbackpupils.fragments.CourseListFragment;
import com.example.jennifer.feedbackpupils.models.Course;
import com.example.jennifer.feedbackpupils.models.Student;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by matej on 2017-01-19.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private final CourseListFragment mCourseListFragment;


    private Student student;
    private final CourseListFragment.OnCourseSelectedListener mCourseSelectedListener;


    private DatabaseReference mDataRef;
    private DatabaseReference mStudentsRef;

    private Student mStudent;

    private ArrayList<Course> mCourses = new ArrayList<>();


    public CourseAdapter(CourseListFragment courseListFragment,
                         CourseListFragment.OnCourseSelectedListener listener) {

        mCourseListFragment = courseListFragment;
        mCourseSelectedListener = listener;


        mDataRef = FirebaseDatabase.getInstance().getReference();

        start();

    }

    public void start(){

        mStudentsRef = mDataRef.child("users/students");
        Query query = mStudentsRef.orderByChild("username").equalTo("Jennifer");
        query.addChildEventListener(new ChildEventListener() {

            public void setCourseList(DataSnapshot dataSnapshot){
                mStudent = dataSnapshot.getValue(Student.class);

                ArrayList<String> subscribedTo = new ArrayList<>();
                Map<String,Boolean> temp = mStudent.getSubscribedto();

                subscribedTo.clear();
                for(String courseKey : temp.keySet()){
                    subscribedTo.add(courseKey);
                }

                listTheCourses(subscribedTo);
                notifyDataSetChanged();

                Log.d("Size of subs","" + subscribedTo.size());

            }
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    setCourseList(dataSnapshot);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                setCourseList(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                setCourseList(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void listTheCourses(ArrayList<String> subscribedTo){
         DatabaseReference mCoursesRef;
         mCoursesRef = mDataRef.child("courses");
        mCourses.clear();
        for(String key : subscribedTo){

        Query queryCourses = mCoursesRef.orderByKey().equalTo(key);
        queryCourses.addChildEventListener(new ChildEventListener() {

            private void add(DataSnapshot dataSnapshot){
                Course course = dataSnapshot.getValue(Course.class);
                course.setKey(dataSnapshot.getKey());
                mCourses.add(course);

            }

            private int remove (String key) {
                for(Course course : mCourses){
                    if(course.getKey().equals(key)){
                        int foundPos = mCourses.indexOf(course);
                        mCourses.remove(course);
                        return foundPos;

                    }
                }
                return -1;
            }

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                add(dataSnapshot);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                remove(dataSnapshot.getKey());
                add(dataSnapshot);
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                int position = remove(dataSnapshot.getKey());
                if (position >= 0) {
                    notifyItemRemoved(position);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_course, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mCourseNameTextView.setText(mCourses.get(position).getName());
        holder.mCourseCodeTextView.setText(mCourses.get(position).getCode());
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }



    // Class that populates the recycler view
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCourseNameTextView;
        private TextView mCourseCodeTextView;


        public ViewHolder(View itemView) {
            super(itemView);

            mCourseNameTextView = (TextView) itemView.findViewById(R.id.list_course_name);
            mCourseCodeTextView = (TextView) itemView.findViewById(R.id.list_course_code);

        }
    }
}
