package org.acme.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entities.AppResponse;
import org.acme.entities.RunningShoes;
import org.acme.repositories.RunningShoesRepository;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Path("/shoes")
public class RunningShoesResource {
    @Inject
    RunningShoesRepository runningShoesRepository;

    @Path("/name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByNameUsingRepository (String name) {
        List<RunningShoes> runningShoesList = runningShoesRepository.findByName(name);
        return !runningShoesList.isEmpty() ?
            Response.ok(
                    AppResponse.<List<RunningShoes>>builder().data(runningShoesList).success(true).build()
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
                    .entity(AppResponse.<List<StackTraceElement>>builder().errorCode("9999" + e.getLocalizedMessage()).success(false).data(Arrays.stream(e.getStackTrace()).toList()).build())
                    .build();
        }
    }

    @Path("/quantity/{quantity}")
    @PUT
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRunningShoeQuantity(RunningShoes runningShoes, @PathParam("quantity") Integer quantity) {
        try {
            runningShoes.setQuantity(quantity);
            RunningShoes runningShoesResult = runningShoesRepository.updateQuantity(runningShoes);
            return runningShoesResult != null ?
                Response.status(Response.Status.CREATED)
                        .entity(AppResponse.builder().errorCode("0000").success(true).data(runningShoesResult).build())
                        .build()
                        :
                Response.status(Response.Status.BAD_REQUEST)
                    .entity(AppResponse.builder().errorCode("0001").success(false).data("Probably the running shoe does not exist.").build())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(AppResponse.<List<StackTraceElement>>builder()
                            .errorCode("9999" + e.getLocalizedMessage())
                            .success(false)
                            .data(Arrays.stream(e.getStackTrace()).toList())
                            .build())
                    .build();
        }
    }

    @Path("/id/{id}")
    @DELETE
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRunningShoesById(@PathParam("id") Long id) {
        try {
            Optional<RunningShoes> optionalRunningShoes = runningShoesRepository.findByIdOptional(id);
            RunningShoes runningShoesResult = null;
            if (optionalRunningShoes.isPresent()){
                runningShoesResult = optionalRunningShoes.get();
                runningShoesRepository.delete(runningShoesResult);
            }

            return runningShoesResult != null ?
                    Response.status(Response.Status.OK)
                            .entity(AppResponse.builder().errorCode("0000").success(true).data(runningShoesResult).build())
                            .build()
                    :
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(AppResponse.builder().errorCode("0001").success(false).data("Probably the running shoe does not exist.").build())
                            .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(AppResponse.<List<StackTraceElement>>builder()
                            .errorCode("9999" + e.getLocalizedMessage())
                            .success(false)
                            .data(Arrays.stream(e.getStackTrace()).toList())
                            .build())
                    .build();
        }
    }

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllByPage(@RestQuery("per_page") Integer perPage, @RestQuery Integer page) {
        try {
            List<RunningShoes> runningShoesList = runningShoesRepository.findAllByPage(page, perPage);
            long countDatas = runningShoesRepository.countTotalData();

            return runningShoesList != null ?
                    Response.status(Response.Status.OK)
                            .entity(AppResponse.builder().errorCode("0000").success(true).data(runningShoesList).count(countDatas).build())
                            .build()
                    :
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(AppResponse.builder().errorCode("0001").success(false).data("Probably the running shoes list is empty.").build())
                            .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(AppResponse.<List<StackTraceElement>>builder()
                            .errorCode("9999" + e.getLocalizedMessage())
                            .success(false)
                            .data(Arrays.stream(e.getStackTrace()).toList())
                            .build())
                    .build();
        }
    }
}
