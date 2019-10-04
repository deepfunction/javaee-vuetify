package coursebuy.rest.registration;

import coursebuy.domain.usecase.registration.RegistrationConfirm;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
public class RegistrationConfirmRest {

    @Inject
    private RegistrationConfirm registrationConfirm;

    @POST
    @Path("/registrationConfirm/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrationConfirm(@PathParam("code") String code, @QueryParam("locale") String locale) {
        return Response.status(OK).entity(registrationConfirm.registrationConfirm(code, locale)).build();
    }
}