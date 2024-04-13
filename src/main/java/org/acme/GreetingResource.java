package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.entities.RunningShoes;
import org.acme.repositories.RunningShoesRepository;

import java.util.Arrays;

@Path("/hello")
public class GreetingResource {

    @Inject
    RunningShoesRepository runningShoesRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        System.out.println("Test:");
        System.out.println(Arrays.toString(runningShoesRepository.listAll().toArray()));
        return "Hello from Quarkus REST";
    }
}
