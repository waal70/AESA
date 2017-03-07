package org.waal70.utils.security.aesa.interfacing;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/")
public class AESAService {
	
	@GET
	@Produces({"application/json"})
	public Response sayUserManual() {
		JSONObject jo = new JSONObject();
		
		jo.put("instructions", "Use /{name} to have me greet <name>");
				
		String output = "Response from REST is: \n\n " + jo.toString();
		return Response.ok(output).build();
				
	}

    @GET
    @Path("/{name}")
    @Produces("text/plain")
    public Response sayHello(@PathParam("name") String msg) {
        String output = "Hello, " + msg + "!";
        return Response.ok(output).build();
    }

}
