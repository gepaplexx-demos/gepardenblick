package com.gepardec;

import javax.ws.rs.Path;

@Path("/orgs")
public class Durchblick {

    @Path("/{Org}/repos")
    public void getAllRepos() {
    }
}
