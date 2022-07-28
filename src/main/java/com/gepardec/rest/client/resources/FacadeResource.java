package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.OrgsRepo;
import com.gepardec.rest.client.model.Repository;
import com.gepardec.rest.client.model.SourceHook;
import com.gepardec.rest.client.services.IOrgsRepoService;
import com.gepardec.rest.client.services.IRepositoryService;
import com.gepardec.rest.client.services.ISourceHookService;
import com.gepardec.visualization.GraphvizService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import java.util.*;
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

    @Inject
    ObjectMapper mapper;

    @Inject
    GraphvizService graphvizService;

    @GET
    @Path("/{org}/hooks")
    @Produces("application/json")
    public String getHooksByOrg(@PathParam String org) throws JsonProcessingException {
        HashSet<Repository> res = repositoryService.getReposByOrg(org);
        HashMap<String, List<String>> hookMap = new HashMap<>();
        for (Repository repo:res) {
            if (repo.permissions.get("admin").equals("true")){
                List<SourceHook> hooks = sourceHookService.getHookByRepos(org, repo.name);
                hookMap.put(repo.name, hooks.stream().map(x -> x.config.get("url")).collect(Collectors.toList()));
            } else{
                hookMap.put(repo.name, null);
            }
        }

        return mapper.writeValueAsString(hookMap);
    }

    @GET
    @Path("/{org}/hooks/graph")
    @Produces({"image/svg+xml"})
    public Response getHooksByOrgGraph(@PathParam String org) {
        HashSet<Repository> res = repositoryService.getReposByOrg(org);
        HashMap<String, List<String>> hookMap = new HashMap<>();
        for (Repository repo:res) {
            if (repo.permissions.get("admin").equals("true")){
                List<SourceHook> hooks = sourceHookService.getHookByRepos(org, repo.name);
                hookMap.put(repo.name, hooks.stream().map(x -> x.config.get("url")).collect(Collectors.toList()));
            } else {
                hookMap.put(repo.name, null);
            }
        }

        return Response.ok(graphvizService.drawGraphFromComplexStringHashMap(hookMap)).build();
    }

    @GET
    @Path("/orgs/repos")
    @Produces("application/json")
    public String getReposFromOrgs() throws JsonProcessingException {
        HashSet<OrgsRepo> resOrgs = orgsRepoService.getOrgByToken();
        HashMap<String, List<String>> repoMap = new HashMap<>();
        for (OrgsRepo orgs:resOrgs) {
            List<Repository> repos = repositoryService.getReposByOrgs(orgs.login);
            repoMap.put(orgs.login, repos.stream().map(x -> x.name).collect(Collectors.toList()));
        }
        return mapper.writeValueAsString(repoMap);
    }

    @GET
    @Path("/orgs/repos/graph")
    @Produces({"image/svg+xml"})
    public Response getReposFromOrgsGraph() {
        HashSet<OrgsRepo> resOrgs = orgsRepoService.getOrgByToken();
        HashMap<String, List<String>> repoMap = new HashMap<>();
        for (OrgsRepo orgs:resOrgs) {
            List<Repository> repos = repositoryService.getReposByOrgs(orgs.login);
            repoMap.put(orgs.login, repos.stream().map(x -> x.name).collect(Collectors.toList()));
        }

        return Response.ok(graphvizService.drawGraphFromComplexStringHashMap(repoMap)).build();
    }

    @GET
    @Path("/orgs/repos/hooks")
    @Produces("application/json")
    public String getAllHooks() throws JsonProcessingException {
        HashSet<OrgsRepo> resOrgs = orgsRepoService.getOrgByToken();
        HashSet<Repository> resRepo;
        HashMap<String, List<String>> hookMap = new HashMap<>();
        for (OrgsRepo orgs:resOrgs) {
            resRepo = repositoryService.getReposByOrg(orgs.login);
            for (Repository repo:resRepo) {
                if (repo.permissions.get("admin").equals("true")){
                    List<SourceHook> hooks = sourceHookService.getHookByRepos(orgs.login, repo.name);
                    List<String> urls = hooks.stream().map(x -> x.config.get("url")).collect(Collectors.toList());

                    // Map.put overrides values when there already exists a mapping for a given key
                    if(hookMap.get(orgs.login) == null) {
                        hookMap.put(orgs.login, urls);
                    } else {
                        hookMap.get(orgs.login).addAll(urls);
                    }
                }// else removed here; would override existing hook list with null -> just ignore and proceed
            }
        }
        return mapper.writeValueAsString(hookMap);
    }

    @GET
    @Path("/orgs/repos/hooks/graph")
    @Produces({"image/svg+xml"})
    public Response getAllHooksGraph() {

        HashSet<OrgsRepo> resOrgs = orgsRepoService.getOrgByToken();
        HashSet<Repository> repos;
        HashMap<String, HashMap<String, List<String>>> orgRepoHookMap = new HashMap<>();
        HashMap<String, List<String>> repoMap = new HashMap<>();

        for (OrgsRepo orgs : resOrgs) {
            repos = repositoryService.getReposByOrg(orgs.login);
            for (Repository repo : repos) {
                if (repo.permissions.get("admin").equals("true")){
                    List<SourceHook> hooks = sourceHookService.getHookByRepos(orgs.login, repo.name);
                    List<String> urls = hooks.stream().map(x -> x.config.get("url")).collect(Collectors.toList());
                    repoMap.put(repo.name, urls);
                }
            }

            HashMap<String, List<String>> copiedRepoMap = copy(repoMap);
            orgRepoHookMap.put(orgs.login, copiedRepoMap);
            repoMap.clear();
        }

        return Response.ok(graphvizService.drawGraphFromNestedStringHashMap(orgRepoHookMap)).build();
    }

    // create a deep copy of given HashMap
    public static HashMap<String, List<String>> copy(HashMap<String, List<String>> original)
    {
        HashMap<String, List<String>> copy = new HashMap<String, List<String>>();
        for (Map.Entry<String, List<String>> entry : original.entrySet())
        {
            copy.put(entry.getKey(), new ArrayList<String>(entry.getValue()));
        }
        return copy;
    }
}
