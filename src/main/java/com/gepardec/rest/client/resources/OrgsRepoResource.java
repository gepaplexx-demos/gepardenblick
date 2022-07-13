package com.gepardec.rest.client.resources;

import com.gepardec.rest.client.model.OrgsRepo;
import com.gepardec.rest.client.services.IOrgsRepoService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.HashSet;

@Path("/user")
public class OrgsRepoResource {
    @Inject
    @RestClient
    IOrgsRepoService orgsRepoService;

    @GET
    @Path("/orgs")
    public HashMap<String, String> getOrgByToke() {
        HashSet<OrgsRepo> res =  orgsRepoService.getOrgByToke();
        HashMap<String, String> repos = new HashMap<>();
        res.forEach(elem -> repos.put(elem.login, elem.url));
        return repos;
    }
}
