package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.SourceHook;
import com.gepardec.rest.client.services.ISourceHookService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.Set;

@Path("/repo")
public class SourceHookResource {

    @Inject
    @RestClient
    ISourceHookService souceHookService;

    @Inject
    ObjectMapper mapper;

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
}