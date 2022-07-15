package com.gepardec.rest.client.resources;

import com.gepardec.rest.client.model.ArgoCdRepo;
import com.gepardec.rest.client.services.IArgoCdRepoService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/v1")
public class ArgoCdRepoResource {
    @Inject
    @RestClient
    IArgoCdRepoService argoCdRepoService;

    @GET
    @Path("/applications")
    public HashMap<String, String> getAllRepos() {
        List<ArgoCdRepo> res =  argoCdRepoService.getAllRepos();
        HashMap<String, String> repos = new HashMap<>();
        ArgoCdRepo argoCdRepo = new ArgoCdRepo();
        Map<String, List<String>> spec = (Map<String, List<String>>) argoCdRepo.items.get("spec");
        Map<String, String> source = (Map<String, String>) spec.get("source");
        argoCdRepo.repoURL = source.get("repoURL");
        repos.put(argoCdRepo.repoURL, (String) argoCdRepo.metadata.get("name"));

        return repos;
    }
}
