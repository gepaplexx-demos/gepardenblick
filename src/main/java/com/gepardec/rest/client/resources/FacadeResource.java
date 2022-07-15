package com.gepardec.rest.client.resources;

import com.gepardec.rest.client.model.OrgsRepo;
import com.gepardec.rest.client.model.Repository;
import com.gepardec.rest.client.model.SourceHook;
import com.gepardec.rest.client.services.IOrgsRepoService;
import com.gepardec.rest.client.services.IRepositoryService;
import com.gepardec.rest.client.services.ISourceHookService;
import io.quarkus.vertx.http.runtime.attribute.ResponseCodeAttribute;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Path("/facade")
public class FacadeResource {

    @Inject
    @RestClient
    IRepositoryService repositoryService;

    @Inject
    @RestClient
    ISourceHookService sourceHookService;

    @Inject
    @RestClient
    IOrgsRepoService orgsRepoService;

    @GET
    @Path("/{org}/hooks")
    public HashMap<String, List<String>> getHookByRepos(@PathParam String org) {
        HashSet<Repository> res = repositoryService.getReposByOrg(org);
        HashMap<String, List<String>> hookMap = new HashMap<>();
        for (Repository repo:res) {
            if (repo.permissions.get("admin").equals("true")){
                List<SourceHook> hooks = sourceHookService.getHookByRepos(org, repo.name);
                hookMap.put(repo.name, hooks.stream().map(x -> x.config.get("url")).collect(Collectors.toList()));// orgs hinzu nehmen?
            }
            else{
                List<String> Lnull = null;
                hookMap.put(repo.name, Lnull);
            }
        }
        return hookMap;
    }

    @GET
    @Path("/orgs/repos")
    public HashMap<String, List<String>> getReposByOrgs() {
        HashSet<OrgsRepo> resOrgs = orgsRepoService.getOrgByToke();
        HashMap<String, List<String>> repoMap = new HashMap<>();
        for (OrgsRepo orgs:resOrgs) {
            List<Repository> repos = repositoryService.getReposByOrgs(orgs.login);
            repoMap.put(orgs.login, repos.stream().map(x -> x.name).collect(Collectors.toList()));
        }
        return repoMap;
    }

    @GET
    @Path("/orgs/repos/hooks")
    public HashMap<String, List<String>> getHooksByOrgs() {
        HashSet<OrgsRepo> resOrgs = orgsRepoService.getOrgByToke();
        HashMap<String, List<String>> repoMap = new HashMap<>();
        HashSet<Repository> resRepo;
        HashMap<String, List<String>> hookMap = new HashMap<>();
        for (OrgsRepo orgs:resOrgs) {
            List<Repository> repos = repositoryService.getReposByOrgs(orgs.login);
            repoMap.put(orgs.login, repos.stream().map(x -> x.name).collect(Collectors.toList()));
            resRepo = repositoryService.getReposByOrg(orgs.login);
            for (Repository repo:resRepo) {
                if (repo.permissions.get("admin").equals("true")){
                    List<SourceHook> hooks = sourceHookService.getHookByRepos(orgs.login, repo.name);
                    hookMap.put(orgs.login, hooks.stream().map(x -> x.config.get("url")).collect(Collectors.toList()));
                }
                else{
                    List<String> Lnull = null;
                    hookMap.put(orgs.login, Lnull);
                }

            }
        }
        return hookMap;
    }

}
