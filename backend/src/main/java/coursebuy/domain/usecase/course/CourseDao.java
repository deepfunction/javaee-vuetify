package coursebuy.domain.usecase.course;

import com.sun.istack.Nullable;
import coursebuy.domain.entity.Course;
import coursebuy.domain.entity.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CourseDao {

    @NotNull
    List<Course> getCourses();

    boolean updateCourses(@NotNull List<Course> courses);

    @Nullable
    User addBoughtCourses(@NotNull String email, @NotNull List<String> boughtCourseNames);
}
