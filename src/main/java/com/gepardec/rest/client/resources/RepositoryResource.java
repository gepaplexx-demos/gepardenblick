package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.Repository;
import com.gepardec.rest.client.services.IRepositoryService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.*;

@Path("/repo")
public class RepositoryResource {

    @Inject
    @RestClient
    IRepositoryService repositoryService;

    @Inject
    ObjectMapper mapper;

    @GET
    @Path("/{org}")
    public String getReposByOrg(@PathParam String org) throws JsonProcessingException {
        HashSet<Repository> res =  repositoryService.getReposByOrg(org);
        HashMap<String, String> repos = new HashMap<>();
        res.forEach(elem -> repos.put(elem.name, elem.html_url));
        return mapper.writeValueAsString(repos);
    }
}
