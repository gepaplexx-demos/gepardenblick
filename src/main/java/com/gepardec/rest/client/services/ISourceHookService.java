package com.gepardec.rest.client.services;

import com.gepardec.rest.client.model.SourceHook;
import com.gepardec.utils.RequestAuthorizationHeaderFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Set;
import java.util.concurrent.CompletionStage;

@Path("/repos")
@RegisterRestClient
@RegisterClientHeaders(RequestAuthorizationHeaderFactory.class)
public interface ISourceHookService {

    @GET
    @Path("/{owner}/{rep}/hooks")
    Set<SourceHook> getHookByUrl(@PathParam String owner, @PathParam String rep);

    @GET
    @Path("/{orgs}/{repos}/hooks")
    Set<SourceHook> getHookByOrgs(@PathParam String orgs, @PathParam String repos);

    @GET
    @Path("/{orgs}/{repos}/hooks")
    Set<SourceHook> getHookByRepos(@PathParam String org, @PathParam String repos);



}
