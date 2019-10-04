package coursebuy.domain.usecase.utils;

import coursebuy.domain.entity.Course;

import java.util.List;

public class Courses {

    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
