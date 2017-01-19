package com.example.jennifer.feedbackpupils;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


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



//import layout.Subscribe;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CourseListFragment.OnCourseSelectedListener

        {


    private DatabaseReference mDataRef;
    private ArrayList<Course> mCourses = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataRef = FirebaseDatabase.getInstance().getReference();
        mDataRef.keepSynced(true);

        getTheCourses();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






        // Make CourseListFragment starting window
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, new CourseListFragment());
        ft.commit();

    }

            public void getTheCourses(){

                DatabaseReference mCoursesRef = mDataRef.child("courses");
                mCoursesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> courses = dataSnapshot.getChildren();

                        for(DataSnapshot ds : courses){

                            Course c = ds.getValue(Course.class);
                            c.setKey(ds.getKey());
                            mCourses.add(c);

                        }
                        Log.d("courses","" + mCourses.size());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

    @Override
    protected void onStart() {
        super.onStart();
       }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        }


    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

            @Override
            public void onCourseClicked(Uri uri) {

            }

            @Override
            public void onSubscribeToCourseClicked(String courseCode) {

                DatabaseReference mStudentRef = mDataRef.child("users/students/randomkey/subscribedto");
                int cnt = 0;
                for(int i = 0; i<mCourses.size();i++){
                    Course temp = mCourses.get(i);
                    if(courseCode.equals(temp.getCode())){
                        mStudentRef.child(temp.getKey()).setValue(true);
                        cnt = 1;
                    }
                }
                if(cnt == 1){
                    Toast.makeText(this, "Course added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Course NOT added", Toast.LENGTH_SHORT).show();

                }

            }
        }

