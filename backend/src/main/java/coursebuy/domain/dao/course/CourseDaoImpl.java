package coursebuy.domain.dao.course;

import coursebuy.domain.entity.Course;
import coursebuy.domain.entity.User;
import coursebuy.domain.usecase.course.CourseDao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@RequestScoped
public class CourseDaoImpl implements CourseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Course> getCourses() {
        return entityManager.createQuery("Select c from Course c", Course.class).getResultList();
    }

    @Override
    public boolean updateCourses(List<Course> courses) {
        List<Course> updatedCourses = new ArrayList<>();
        for (Course course : courses) {
            updatedCourses.add(entityManager.merge(course));
        }
        return updatedCourses.isEmpty();
    }

    @Override
    public User addBoughtCourses(String email, List<String> boughtCourseNames) {
        User user = (User) entityManager.createQuery("select u from User u where u.email = :email").setParameter("email", email).getSingleResult();
        user.getBoughtCourses().addAll(boughtCourseNames);
        return entityManager.merge(user);
    }
}
