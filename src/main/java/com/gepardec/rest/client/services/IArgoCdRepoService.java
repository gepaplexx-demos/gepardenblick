package com.gepardec.rest.client.services;

import com.gepardec.utils.RequestAuthorizationArgoCdHeaderFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/v1")
@RegisterRestClient
@RegisterClientHeaders(RequestAuthorizationArgoCdHeaderFactory.class)
public interface IArgoCdRepoService {
    @GET
    @Path("/applications")
    String getAllRepos();

}
