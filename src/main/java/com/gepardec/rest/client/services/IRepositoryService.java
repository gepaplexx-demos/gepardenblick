package com.gepardec.rest.client.services;

import com.gepardec.rest.client.model.Repository;
import com.gepardec.utils.RequestAuthorizationHeaderFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashSet;

@Path("/orgs")
@RegisterRestClient
@RegisterClientHeaders(RequestAuthorizationHeaderFactory.class)
public interface IRepositoryService {

    @GET
    @Path("/{org}/repos")
    HashSet<Repository> getReposByOrg(@PathParam String org);
}
