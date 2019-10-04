package coursebuy.domain.usecase.course;

import coursebuy.domain.entity.Course;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@RequestScoped
public class CourseGetter {

    @Inject
    private CourseDao courseDao;

    public CourseResult getCourses(String language){
        Locale locale = new Locale.Builder().setLanguage(language).build();
        List<Course> courses = courseDao.getCourses();
        if (courses.isEmpty()) {
            return new CourseResult(ResourceBundle.getBundle("i18n.messages", locale).getString("somethingWrong"), false);
        } else {
            return new CourseResult(ResourceBundle.getBundle("i18n.messages", locale).getString("loginSuccessful"), true, courses);
        }
    }
}
