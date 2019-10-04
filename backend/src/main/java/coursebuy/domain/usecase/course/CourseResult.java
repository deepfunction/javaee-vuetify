package coursebuy.domain.usecase.course;

import coursebuy.domain.entity.Course;
import coursebuy.domain.usecase.utils.BaseResult;

import java.util.ArrayList;
import java.util.List;

public class CourseResult extends BaseResult {

    private List<Course> courses = new ArrayList<>();

    CourseResult(String message, boolean isSuccess) {
        super(message, isSuccess);
    }

    CourseResult(String message, boolean isSuccess, List<Course> courses) {
        super(message, isSuccess);
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
