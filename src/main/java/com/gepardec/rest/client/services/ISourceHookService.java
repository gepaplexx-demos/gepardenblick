package com.gepardec.rest.client.services;

import com.gepardec.rest.client.model.SourceHook;
import com.gepardec.utils.RequestAuthorizationHeaderFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.*;
import java.util.*;

@Path("/repos")
@RegisterRestClient
@RegisterClientHeaders(RequestAuthorizationHeaderFactory.class)
public interface ISourceHookService {

    @GET
    @Path("/{owner}/{rep}/hooks")
    Set<SourceHook> getHookByOwnerAndRepo(@PathParam String owner, @PathParam String rep);

    @GET
    @Path("/{org}/{repos}/hooks")
    List<SourceHook> getHookByRepos(@PathParam String org, @PathParam String repos);



}
