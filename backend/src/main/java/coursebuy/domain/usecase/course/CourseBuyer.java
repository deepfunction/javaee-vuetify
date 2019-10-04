package coursebuy.domain.usecase.course;

import coursebuy.domain.entity.Course;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@RequestScoped
public class CourseBuyer {

    @Inject
    CourseDao courseDao;

    public CourseResult completeCheckout(List<Course> courses, String language) {
        Locale locale = new Locale.Builder().setLanguage(language).build();
        if (courseDao.updateCourses(courses)) {
            return new CourseResult(ResourceBundle.getBundle("i18n.messages", locale).getString("somethingWrong"), false);
        } else {
            List<String> boughtCourseNames = new ArrayList<>();
            String userEmail = courses.get(0).getUserEmail();;
            for(Course course : courses) {
                boughtCourseNames.add(course.getName());
            }
            if (courseDao.addBoughtCourses(userEmail, boughtCourseNames) != null) {
                return new CourseResult(ResourceBundle.getBundle("i18n.messages", locale).getString("boughtSuccessful"), true);
            } else {
                return new CourseResult(ResourceBundle.getBundle("i18n.messages", locale).getString("somethingWrong"), false);
            }
        }
    }
}
