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

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    @Inject
    ObjectMapper mapper;

    @Inject
    GraphvizService graphvizService;

    @GET
    @Path("/{org}/hooks")
    public String getHookByRepos(@PathParam String org) throws JsonProcessingException {
        HashSet<Repository> res = repositoryService.getReposByOrg(org);
        HashMap<String, List<String>> hookMap = new HashMap<>();
        for (Repository repo:res) {
            if (repo.permissions.get("admin").equals("true")){
                List<SourceHook> hooks = sourceHookService.getHookByRepos(org, repo.name);
                hookMap.put(repo.name, hooks.stream().map(x -> x.config.get("url")).collect(Collectors.toList()));
            }
            else{
                hookMap.put(repo.name, null);
            }
        }

        return mapper.writeValueAsString(hookMap);
    }

    @GET
    @Path("/{org}/hooks/graph")
    @Produces({"image/png"})
    public Response getHookByReposGraph(@PathParam String org) throws IOException {

        HashSet<Repository> res = repositoryService.getReposByOrg(org);
        HashMap<String, List<String>> hookMap = new HashMap<>();
        for (Repository repo:res) {
            if (repo.permissions.get("admin").equals("true")){
                List<SourceHook> hooks = sourceHookService.getHookByRepos(org, repo.name);
                hookMap.put(repo.name, hooks.stream().map(x -> x.config.get("url")).collect(Collectors.toList()));
            }
            else{
                hookMap.put(repo.name, null);
            }
        }

        BufferedImage image = graphvizService.drawGraphFromComplexStringHashMap(hookMap);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] imageData = outputStream.toByteArray();

        return Response.ok(imageData).build();
    }

    @GET
    @Path("/orgs/repos")
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
    @Produces({"image/png"})
    public Response getReposFromOrgsGraph() throws IOException {
        HashSet<OrgsRepo> resOrgs = orgsRepoService.getOrgByToken();
        HashMap<String, List<String>> repoMap = new HashMap<>();
        for (OrgsRepo orgs:resOrgs) {
            List<Repository> repos = repositoryService.getReposByOrgs(orgs.login);
            repoMap.put(orgs.login, repos.stream().map(x -> x.name).collect(Collectors.toList()));
        }

        BufferedImage image = graphvizService.drawGraphFromComplexStringHashMap(repoMap);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] imageData = outputStream.toByteArray();

        return Response.ok(imageData).build();
    }

    @GET
    @Path("/orgs/repos/hooks")
    public String getAllHooks() throws JsonProcessingException {
        HashSet<OrgsRepo> resOrgs = orgsRepoService.getOrgByToken();
        HashSet<Repository> resRepo;
        HashMap<String, List<String>> hookMap = new HashMap<>();
        for (OrgsRepo orgs:resOrgs) {
            resRepo = repositoryService.getReposByOrg(orgs.login);
            for (Repository repo:resRepo) {
                if (repo.permissions.get("admin").equals("true")){
                    List<SourceHook> hooks = sourceHookService.getHookByRepos(orgs.login, repo.name);
                    hookMap.put(orgs.login, hooks.stream().map(x -> x.config.get("url")).collect(Collectors.toList()));
                }
                else{
                    hookMap.put(orgs.login, null);
                }
            }
        }
        return mapper.writeValueAsString(hookMap);
    }


    @GET
    @Path("/orgs/repos/hooks/graph")
    @Produces({"image/png"})
    public Response getAllHooksGraph() throws IOException {

        HashSet<OrgsRepo> resOrgs = orgsRepoService.getOrgByToken();
        HashSet<Repository> resRepo;
        HashMap<String, List<String>> hookMap = new HashMap<>();
        for (OrgsRepo orgs:resOrgs) {
            resRepo = repositoryService.getReposByOrg(orgs.login);
            for (Repository repo:resRepo) {
                if (repo.permissions.get("admin").equals("true")){
                    List<SourceHook> hooks = sourceHookService.getHookByRepos(orgs.login, repo.name);
                    hookMap.put(orgs.login, hooks.stream().map(x -> x.config.get("url")).collect(Collectors.toList()));
                }
                else{
                    hookMap.put(orgs.login, null);
                }
            }
        }

        BufferedImage image = graphvizService.drawGraphFromComplexStringHashMap(hookMap);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] imageData = outputStream.toByteArray();

        return Response.ok(imageData).build();
    }
}
