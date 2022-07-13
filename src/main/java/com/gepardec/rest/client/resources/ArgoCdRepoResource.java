package com.gepardec.rest.client.resources;

import com.gepardec.rest.client.model.ArgoCdRepo;
import com.gepardec.rest.client.services.IArgoCdRepoService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.HashSet;

@Path("/v1")
public class ArgoCdRepoResource {
    @Inject
    @RestClient
    IArgoCdRepoService argoCdRepoService;

    @GET
    @Path("/applications")
    public HashMap<HashMap, String> getOrgByToke() {
        HashSet<ArgoCdRepo> res =  argoCdRepoService.getReposByUrl();
        HashMap<HashMap, String> repos = new HashMap<>();
        res.forEach(elem -> repos.put(elem.items, elem.repoURL));
        return repos;
    }
}
