/**
 * 
 */
package org.waal70.utils.security.aesa.interfacing;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.waal70.utils.security.aesa.AESAEngine;

/**
 * @author awaal
 *
 */
@Path("encode")
public class RESTEncode {
	
	@GET
	public Response sayManual()
	{
		String output = "Please provide /{input}?passphrase=\"apassphrase\". I'll encode input and provide the result.";
		return Response.ok(output).build();
	}
	
	@GET
	@Path("/{input}")
	@Produces("text/plain")
	public Response encodeInput(@PathParam("input") String msg, 
			@DefaultValue("defaultpassphrase") @QueryParam("passphrase") String passPhrase)
	{
		
		String input = msg;
		AESAEngine et = new AESAEngine();
		et.stringTest(input, passPhrase);
		String output = "Input was: " + input + "\n";
		output = output + "Passphrase was: " + passPhrase + "\n";
		output = output + "\n";
		try {
			output = output + "Output is: " + URLEncoder.encode(et.getEncodedResult(),"UTF-8") + "\n";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		output = output + "Achieved in " + et.getEncodeTime() + "ms \n";
		
		return Response.ok(output).build();
	}

}
