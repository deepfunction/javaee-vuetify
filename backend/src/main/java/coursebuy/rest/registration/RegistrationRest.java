package coursebuy.rest.registration;

import coursebuy.domain.usecase.registration.Registration;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
public class RegistrationRest {

    @Inject
    private Registration registration;

    @GET
    @Path("/registration/{email}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@PathParam("email") String email, @PathParam("password") String password, @QueryParam("locale") String locale) {
        return Response.status(OK).entity(registration.register(email, password, locale)).build();
    }
}
