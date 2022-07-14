package com.gepardec.rest.client.resources;

import com.gepardec.rest.client.model.OrgsRepo;
import com.gepardec.rest.client.model.Repository;
import com.gepardec.rest.client.model.SourceHook;
import com.gepardec.rest.client.services.IOrgsRepoService;
import com.gepardec.rest.client.services.IRepositoryService;
import com.gepardec.rest.client.services.ISourceHookService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Path("/user")
public class OrgsRepoResource {
    @Inject
    @RestClient
    IOrgsRepoService orgsRepoService;

    @Inject
    @RestClient
    IRepositoryService repositoryService;

    @Inject
    @RestClient
    ISourceHookService souceHookService;

    HashMap<String, String> orgs = new HashMap<>();
    HashMap<String, String> repos = new HashMap<>();
    HashMap<String, Boolean> hooks = new HashMap<>();

    @GET
    @Path("/orgs")
    public HashMap<String, String> getOrgByToke() {
        HashSet<OrgsRepo> resOrg =  orgsRepoService.getOrgByToke();
        resOrg.forEach(elem -> orgs.put(elem.login, elem.url));
        return orgs;
    }

    /*@GET
    @Path("/orgs/repos")
    public HashMap<String, String> getReposByOrgs(String org) {
        HashSet<Repository> resRepo =  repositoryService.getReposByOrgs(org);
        resRepo.forEach(elem -> repos.put(elem.name, elem.hooks_url));
        return repos;
    }

    @GET
    @Path("/orgs/repos/hooks")
    public HashMap<String, Boolean> getHookByRepos(String orgs, String repos) {
        Set<SourceHook> resHook = (Set<SourceHook>) souceHookService.getHookByOrgs(orgs, repos);
        for (SourceHook s: resHook) {
            hooks.put(s.config.get("url"), s.active);
        }
        return hooks;
    } */

}
