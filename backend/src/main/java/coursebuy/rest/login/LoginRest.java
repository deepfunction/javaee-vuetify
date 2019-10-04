package coursebuy.rest.login;

import coursebuy.domain.usecase.login.Login;

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
public class LoginRest {

    @Inject
    private Login login;

    @GET
    @Path("/login/{email}/{password}/{deviceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@PathParam("email") String email, @PathParam("password") String password, @PathParam("deviceId") String deviceId, @QueryParam("locale") String locale) {
        return Response.status(OK).entity(login.login(email, password, deviceId, locale)).build();
    }

    @GET
    @Path("/loginWithToken/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginWithToken(@PathParam("token") String token, @QueryParam("locale") String locale) {
        return Response.status(OK).entity(login.loginWithToken(token, locale)).build();
    }
}
