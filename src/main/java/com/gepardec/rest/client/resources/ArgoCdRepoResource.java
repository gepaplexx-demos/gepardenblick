package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.ArgoCdRepo;
import com.gepardec.rest.client.services.IArgoCdRepoService;
import com.gepardec.visualization.GraphvizService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

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
import java.util.HashSet;

@Path("/argo")
public class ArgoCdRepoResource {
    @Inject
    @RestClient
    IArgoCdRepoService argoCdRepoService;

    @Inject
    ObjectMapper mapper;

    @Inject
    GraphvizService graphvizService;

    @GET
    @Path("/repos")
    public String getAllRepos() throws JsonProcessingException {

        String response =  argoCdRepoService.getAllRepos();
        TypeReference<HashSet<ArgoCdRepo>> typeRef = new TypeReference<HashSet<ArgoCdRepo>>() {};

        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        HashSet<ArgoCdRepo> repos = mapper.readValue(response, typeRef);

        HashMap<String, String> result = new HashMap<>();
        repos.forEach(repo -> repo.items.forEach(item -> result.put(item.name, item.repoURL)));
        return mapper.writeValueAsString(result);
    }

    @GET
    @Path("/repos/graph")
    @Produces({"image/png"})
    public Response getAllReposGraph() throws IOException {

        String response =  argoCdRepoService.getAllRepos();
        TypeReference<HashSet<ArgoCdRepo>> typeRef = new TypeReference<HashSet<ArgoCdRepo>>() {};

        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        HashSet<ArgoCdRepo> repos = mapper.readValue(response, typeRef);

        HashMap<String, String> result = new HashMap<>();
        repos.forEach(repo -> repo.items.forEach(item -> result.put(item.name, item.repoURL)));

        BufferedImage image = graphvizService.drawGraphFromSimpleStringHashMap(result);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] imageData = outputStream.toByteArray();

        return Response.ok(imageData).build();
    }
}