package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.SourceHook;
import com.gepardec.rest.client.services.ISourceHookService;
import com.gepardec.visualization.GraphvizService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

@Path("/repo")
public class SourceHookResource {

    @Inject
    @RestClient
    ISourceHookService souceHookService;

    @Inject
    ObjectMapper mapper;

    @Inject
    GraphvizService graphvizService;

    @GET
    @Path("/{owner}/{rep}/hooks")
    public String getHookByOwnerAndRepo(@PathParam String owner, String rep) throws JsonProcessingException {
        Set<SourceHook> res =  souceHookService.getHookByOwnerAndRepo(owner, rep);
        HashMap<String, Boolean> hooks = new HashMap<>();
        for (SourceHook s: res) {
            hooks.put(s.config.get("url"), s.active);
        }
        return mapper.writeValueAsString(hooks);
    }

    @GET
    @Path("/{owner}/{rep}/hooks/graph")
    @Produces({"image/png"})
    public Response getHookByOwnerAndRepoGraph(@PathParam String owner, String rep) throws IOException {
        Set<SourceHook> res =  souceHookService.getHookByOwnerAndRepo(owner, rep);
        HashMap<String, Boolean> hooks = new HashMap<>();
        for (SourceHook s: res) {
            hooks.put(s.config.get("url"), s.active);
        }
        BufferedImage image = graphvizService.drawGraphFromBooleanHashMap(hooks);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] imageData = outputStream.toByteArray();

        return Response.ok(imageData).build();
    }
}