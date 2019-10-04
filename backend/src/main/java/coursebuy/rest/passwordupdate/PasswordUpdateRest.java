package coursebuy.rest.passwordupdate;

import coursebuy.domain.usecase.passwordupdate.PasswordUpdate;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
public class PasswordUpdateRest {

    @Inject
    private PasswordUpdate passwordUpdate;

    @GET
    @Path("/sendPasswordUpdateEmail/{email}")
    public Response sendPasswordUpdateLink(@PathParam("email") String email, @QueryParam("locale") String locale) {
        return Response.status(OK).entity(passwordUpdate.sendPasswordUpdateLink(email, locale)).build();
    }

    @PUT
    @Path("/updatePassword/{code}/{password}")
    public Response updatePassword(@PathParam("code") String code, @PathParam("password") String password, @QueryParam("locale") String locale) {
        return Response.status(OK).entity(passwordUpdate.updatePassword(code, password, locale)).build();
    }
}
