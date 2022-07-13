package com.gepardec.rest.client.services;

import com.gepardec.rest.client.model.OrgsRepo;
import com.gepardec.utils.RequestAuthorizationHeaderFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashSet;

@Path("/user")
@RegisterRestClient
@RegisterClientHeaders(RequestAuthorizationHeaderFactory.class)
public interface IOrgsRepoService {
    @GET
    @Path("/orgs")
    HashSet<OrgsRepo> getOrgByToke();

}
