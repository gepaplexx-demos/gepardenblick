package com.gepardec.rest.client.resources;

import com.gepardec.rest.client.model.ArgoCdRepo;
import com.gepardec.rest.client.services.IArgoCdRepoService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1")
public class ArgoCdRepoResource {
    @Inject
    @RestClient
    IArgoCdRepoService argoCdRepoService;

    @GET
    @Path("/applications")
    public HashMap<String, String> getAllRepos() {
        ArgoCdRepo res =  argoCdRepoService.getAllRepos();
//        HashMap<String, List<String>> repos = new HashMap<>();
        List<List<String>> meta = res.items.stream().map(x -> x.metadata).collect(Collectors.toList());
        List<List<String>> repoURL = res.items.stream().map(x -> x.spec.stream().map(y -> y.source.get(0)).collect(Collectors.toList())).collect(Collectors.toList());
        System.out.println(meta + " " + repoURL);

        return new HashMap<>();
    }
}
