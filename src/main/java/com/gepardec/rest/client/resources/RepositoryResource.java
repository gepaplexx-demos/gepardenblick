package com.gepardec.rest.client.resources;

import com.gepardec.rest.client.model.Repository;
import com.gepardec.rest.client.services.IRepositoryService;
import com.gepardec.rest.client.services.ISourceHookService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.HashSet;

@Path("/repo")
public class RepositoryResource {

    @Inject
    @RestClient
    IRepositoryService repositoryService;

    @Inject
    @RestClient
    ISourceHookService souceHookService;

    @GET
    @Path("/{org}")
    public HashMap<String, String> getReposByOrg(@PathParam String org) {
        HashSet<Repository> res =  repositoryService.getReposByOrg(org);
        HashMap<String, String> repos = new HashMap<>();
        res.forEach(elem -> repos.put(elem.name, elem.hooks_url));
        return repos;
    }
}
