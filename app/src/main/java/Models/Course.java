package Models;

/**
 * Created by jennifer on 2017-01-10.
 */


public class Course {

    private String courseName;
    private String courseID;

    public Course(String courseName, String courseID) {
        this.courseName = courseName;
        this.courseID = courseID;

    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseID() {
        return courseID;
    }


        }

