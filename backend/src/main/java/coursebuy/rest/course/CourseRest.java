package coursebuy.rest.course;

import coursebuy.domain.entity.Course;
import coursebuy.domain.usecase.utils.Courses;
import coursebuy.domain.usecase.course.CourseBuyer;
import coursebuy.domain.usecase.course.CourseGetter;
import coursebuy.rest.utils.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
public class CourseRest {

    @Inject
    private CourseGetter courseGetter;
    @Inject
    private CourseBuyer courseBuyer;

    @GET
    @Path("/getCourses")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses(@QueryParam("locale") String locale) {
        return Response.status(OK).entity(courseGetter.getCourses(locale)).build();
    }

    @PUT
    @Path("/completeCheckout/")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response completeCheckout(Courses courses, @QueryParam("locale") String locale) {
        List<Course> courseList = new ArrayList<>(courses.getCourses());
        return Response.status(OK).entity(courseBuyer.completeCheckout(courseList, locale)).build();
    }
}
