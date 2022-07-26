package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.Repository;
import com.gepardec.rest.client.services.IRepositoryService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;

@QuarkusTest
public class RepositoryResourceTests {

    private final String TEST_ORG = "gepaplexx-demos";

    @InjectMock(convertScopes = true)
    @RestClient
    IRepositoryService repositoryService;

    @Inject
    RepositoryResource repositoryResource;

    @Inject
    ObjectMapper mapper;

    private HashSet<Repository> testRepos;
    private HashMap<String, String> testPermissions;
    private HashMap<String, String> expectedResponse;

    @BeforeEach
    public void setup() {
        testRepos = new HashSet<>();
        testPermissions = new HashMap<>();
        expectedResponse = new HashMap<>();
    }

    @Test
    public void whenGetReposByOrgGivenOrgName_thenReturnValidJsonOfSingleRepo() throws JsonProcessingException {
        testPermissions.put("admin", "true");
        Repository repository = new Repository("gepardenblick", "https://github.com/gepaplexx-demos/gepardenblick", testPermissions);
        testRepos.add(repository);
        expectedResponse.put(repository.name, repository.html_url);
        Mockito.when(repositoryService.getReposByOrg(TEST_ORG)).thenReturn(testRepos);

        Assertions.assertEquals(repositoryResource.getReposByOrg(TEST_ORG), mapper.writeValueAsString(expectedResponse));
    }
}
