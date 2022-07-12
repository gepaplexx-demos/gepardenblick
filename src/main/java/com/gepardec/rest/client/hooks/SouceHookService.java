package com.gepardec.rest.client.hooks;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Set;

@Path("/repos")
@RegisterClientHeaders(RequestUUIDHeaderFactory.class)
@RegisterRestClient(configKey="sourcehook-api")
public interface SouceHookService {

    @GET
    Set<SourceHook> getByRepoUrl(@QueryParam String hookname);

}
