package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.ArgoCdRepo;
import com.gepardec.rest.client.services.IArgoCdRepoService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.HashSet;

@Path("/argo")
public class ArgoCdRepoResource {
    @Inject
    @RestClient
    IArgoCdRepoService argoCdRepoService;

    @Inject
    ObjectMapper mapper;

    @GET
    @Path("/repos")
    public HashMap<String, String> getAllRepos() throws JsonProcessingException {

        String response =  argoCdRepoService.getAllRepos();
        TypeReference<HashSet<ArgoCdRepo>> typeRef = new TypeReference<HashSet<ArgoCdRepo>>() {};

        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        HashSet<ArgoCdRepo> repos = mapper.readValue(response, typeRef);

        HashMap<String, String> result = new HashMap<>();
        repos.forEach(repo -> repo.items.forEach(item -> result.put(item.name, item.repoURL)));

        return result;
    }
}
