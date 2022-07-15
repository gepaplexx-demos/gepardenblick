package com.gepardec.rest.client.resources;

import com.gepardec.rest.client.model.SourceHook;
import com.gepardec.rest.client.services.ISourceHookService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/repo")
public class SourceHookResource {

    @Inject
    @RestClient
    ISourceHookService souceHookService;

    @GET
    @Path("/{owner}/{rep}/hooks")
    public HashMap<String, Boolean> getHookByUrl(@PathParam String owner, String rep) {
        Set<SourceHook> res =  souceHookService.getHookByUrl(owner, rep);
        HashMap<String, Boolean> hooks = new HashMap<>();
        for (SourceHook s: res) {
            hooks.put(s.config.get("url"), s.active);
        }
        return hooks;
    }
}