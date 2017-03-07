package org.waal70.utils.security.aesa.interfacing;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author awaal
 * This class is used by Jersey
 * AESA uses descriptor-less deployment (aka no web.xml)
 * The packages are inspected by Jersey and this means the config
 * takes place through specialized classes.
 * 
 *  This one only sets the base URL for the services.
 *  
 *  Dependency needed for Jersey to work:
 *  jersey-container-servlet (this is the servlet that will serve the REST)
 *  
 *  NOTE: For Tomcat, an additional dependency is needed:
 *  jaxrs-ri
 */
@ApplicationPath("/")
public class AESARESTConfig extends ResourceConfig {

}
