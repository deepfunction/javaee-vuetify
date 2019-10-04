package coursebuy.rest.logout;

import coursebuy.domain.usecase.logout.Logout;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class LogoutRest {

    @Inject
    private Logout logout;

    @POST
    @Path("/deleteAccessTokenByDeviceId/{deviceId}")
    public void deleteAccessTokenByDeviceId(@PathParam("deviceId") String deviceId) {
        logout.deleteAccessTokenByDeviceId(deviceId);
    }


}
