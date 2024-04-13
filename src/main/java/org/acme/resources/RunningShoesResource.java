package org.acme.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entities.AppResponse;
import org.acme.entities.RunningShoes;
import org.acme.repositories.RunningShoesRepository;

import java.util.Arrays;
import java.util.List;

@Path("/shoes")
public class RunningShoesResource {
    @Inject
    RunningShoesRepository runningShoesRepository;

    @Path("/name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByNameUsingRepositoryg (String name) {
        List<RunningShoes> runningShoesList = runningShoesRepository.findByName(name);
        return !runningShoesList.isEmpty() ?
            Response.ok(
                    AppResponse.<RunningShoes>builder().data(runningShoesList).success(true).build()
                    ,MediaType.APPLICATION_JSON).build()
                : Response.status(Response.Status.NOT_FOUND).
                    entity(AppResponse.builder().errorCode("9999").success(false).build())
                    .build();
    }

    @Path("/new")
    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveARunningShoe(RunningShoes runningShoes) {
        try {
            runningShoesRepository.save(runningShoes);
            return Response.status(Response.Status.CREATED)
                    .entity(AppResponse.builder().errorCode("0000").success(true).build())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(AppResponse.<StackTraceElement>builder().errorCode("9999" + e.getLocalizedMessage()).success(false).data(Arrays.stream(e.getStackTrace()).toList()).build())
                    .build();
        }
    }
}
