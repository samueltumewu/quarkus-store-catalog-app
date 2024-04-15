package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.repositories.RunningShoesRepository;
import org.jboss.logging.Logger;

import java.util.Arrays;

@Path("/hello")
public class GreetingResource {

    private static final Logger log = Logger.getLogger(GreetingResource.class);
    @Inject
    RunningShoesRepository runningShoesRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        log.info("Test:");
        log.info(Arrays.toString(runningShoesRepository.listAll().toArray()));
        return "Hello! Welcome to quarkus-store-catalog-app. \nCurrently is on development by samueltumewu. \nPlease check readme to access and test the api. \nThanks!";
    }
}
