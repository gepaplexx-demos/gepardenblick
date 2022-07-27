package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.Repository;
import com.gepardec.rest.client.services.IRepositoryService;
import com.gepardec.visualization.GraphvizService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Path("/repo")
public class RepositoryResource {

    @Inject
    @RestClient
    IRepositoryService repositoryService;

    @Inject
    ObjectMapper mapper;

    @Inject
    GraphvizService graphvizService;

    @GET
    @Path("/{org}")
    @Produces("application/json")
    public String getReposByOrg(@PathParam String org) throws JsonProcessingException {
        HashSet<Repository> res =  repositoryService.getReposByOrg(org);
        HashMap<String, String> repos = new HashMap<>();
        res.forEach(elem -> repos.put(elem.name, elem.html_url));
        return mapper.writeValueAsString(repos);
    }

    @GET
    @Path("/{org}/graph")
    @Produces({"image/svg+xml"})
    public Response getReposByOrgGraph(@PathParam String org) {
        HashSet<Repository> res =  repositoryService.getReposByOrg(org);
        HashMap<String, String> repos = new HashMap<>();
        res.forEach(elem -> repos.put(elem.name, elem.html_url));

        return Response.ok(graphvizService.drawGraphFromSimpleStringHashMap(repos)).build();
    }
}
