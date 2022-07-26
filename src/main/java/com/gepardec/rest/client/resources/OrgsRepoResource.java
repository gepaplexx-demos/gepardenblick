package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.OrgsRepo;
import com.gepardec.rest.client.services.IOrgsRepoService;
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

@Path("/user")
public class OrgsRepoResource {
    @Inject
    @RestClient
    IOrgsRepoService orgsRepoService;

    @Inject
    ObjectMapper mapper;

    @Inject
    GraphvizService graphvizService;

    @GET
    @Path("/orgs")
    public String getOrgByToken() throws JsonProcessingException {
        HashSet<OrgsRepo> resOrg =  orgsRepoService.getOrgByToken();
        HashMap<String, String> orgs = new HashMap<>();
        resOrg.forEach(elem -> orgs.put(elem.login, elem.url));
        return mapper.writeValueAsString(orgs);
    }

    @GET
    @Path("/orgs/graph")
    @Produces({"image/png"})
    public Response getOrgByTokenGraph() throws IOException {
        HashSet<OrgsRepo> resOrg =  orgsRepoService.getOrgByToken();
        HashMap<String, String> orgs = new HashMap<>();
        resOrg.forEach(elem -> orgs.put(elem.login, elem.url));

        BufferedImage image = graphvizService.drawGraphFromSimpleStringHashMap(orgs);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] imageData = outputStream.toByteArray();

        return Response.ok(imageData).build();
    }
}
