package com.example.jennifer.feedbackpupils.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jennifer.feedbackpupils.R;
import com.example.jennifer.feedbackpupils.adapter.CourseAdapter;
import com.example.jennifer.feedbackpupils.models.Course;
import com.example.jennifer.feedbackpupils.models.Student;

import java.util.ArrayList;

public class CourseListFragment extends Fragment {





    private CourseAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private OnCourseSelectedListener mClickListener;


    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getContext();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);



        mRecyclerView = (RecyclerView) view.findViewById(R.id.course_list_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showSubscribeToCourseDialog(null);

            }
        });

        fab.setVisibility(View.VISIBLE);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.course_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        registerForContextMenu(recyclerView);
        mAdapter = new CourseAdapter(this, mClickListener);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @SuppressLint("InflateParams")
    public void showSubscribeToCourseDialog(final Course course){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_subscribe_to_course, null);

        final EditText courseCodeEditText = (EditText) view.findViewById(R.id.dialog_subscribe_course_code);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String courseCode = courseCodeEditText.getText().toString();


                mClickListener.onSubscribeToCourseClicked(courseCode);

            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void onButtonPressed(Uri uri) {
        if (mClickListener != null) {
            mClickListener.onCourseClicked(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCourseSelectedListener) {
            mClickListener = (OnCourseSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mClickListener = null;
    }



    public interface OnCourseSelectedListener {


        void onCourseClicked(Uri uri);
        void onSubscribeToCourseClicked(String courseCode);

    }
}
