package com.gepardec.rest.client.hooks;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Set;
import java.util.concurrent.CompletionStage;

@Path("/repos")
public class SourceHookResource {

    @Inject
    @RestClient
    SouceHookService souceHookService;

    @GET
    @Path("/{Owner}/{Repo}/hooks")
    public Set<SourceHook> hook(@PathParam String hookname) {
        return souceHookService.getByRepoUrl(hookname);
    }
}
