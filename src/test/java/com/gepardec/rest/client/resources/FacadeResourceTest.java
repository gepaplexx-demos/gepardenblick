package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.OrgsRepo;
import com.gepardec.rest.client.model.Repository;
import com.gepardec.rest.client.model.SourceHook;
import com.gepardec.rest.client.services.IOrgsRepoService;
import com.gepardec.rest.client.services.IRepositoryService;
import com.gepardec.rest.client.services.ISourceHookService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@QuarkusTest
public class FacadeResourceTest {

    @InjectMock
    @RestClient
    IOrgsRepoService orgsRepoService;

    @InjectMock
    @RestClient
    ISourceHookService sourceHookService;

    @InjectMock
    @RestClient
    IRepositoryService repositoryService;

    @Inject
    FacadeResource facadeResource;

    @Inject
    ObjectMapper mapper;

    private List<SourceHook> testFacadeHook;
    private List<Repository> testFacadeRepo;
    private HashMap<String, String> testConfig;
    private HashMap<String, String> testPermissions;
    private HashMap<String,  String> expectedResponse;
    private HashMap<String,  HashMap<String, String>> expectedResponseHooks;



    @BeforeEach
    public void setup() {
        testFacadeHook = new ArrayList<>();
        testFacadeRepo = new ArrayList<>();
        testConfig = new HashMap<>();
        expectedResponse = new HashMap<>();
        expectedResponseHooks = new HashMap<>();
        testPermissions = new HashMap<>();
    }

    @Test
    public void whenGetHooksByRepos_thenReturnValidJsonOfHooks() throws JsonProcessingException {
        testPermissions.put("admin", "true");
        testConfig.put("https://gepardenblick.apps.play.gepaplexx.com/push", "https://gepardenblick.apps.play.gepaplexx.com/delete");
        Repository repository = new Repository("gepardenblick", "https://github.com/gepaplexx-demos/gepardenblick", testPermissions);
        SourceHook sourceHook = new SourceHook(Boolean.TRUE, testConfig);
        testFacadeHook.add(sourceHook);
        expectedResponseHooks.put(repository.name, sourceHook.config);
        Mockito.when(sourceHookService.getHookByRepos("gepaplexx-demos", repository.name)).thenReturn(testFacadeHook);

        Assertions.assertEquals(facadeResource.getHookByRepos("gepaplexx-demos"), mapper.writeValueAsString(expectedResponseHooks));
    }

    @Test
    public void whenGetReposFromOrgs_thenReturnValidJsonOfRepos() throws JsonProcessingException {
        testPermissions.put("admin", "true");
        OrgsRepo orgsRepo = new OrgsRepo("gepaplexx-demos", "https://api.github.com/orgs/gepaplexx-demos");
        Repository repository = new Repository("gepardenblick", "https://github.com/gepaplexx-demos/gepardenblick", testPermissions);
        testFacadeRepo.add(repository);
        expectedResponse.put(orgsRepo.login, repository.name);
        Mockito.when(repositoryService.getReposByOrgs(orgsRepo.login)).thenReturn(testFacadeRepo);

        Assertions.assertEquals(facadeResource.getReposFromOrgs(), mapper.writeValueAsString(expectedResponse));
    }

    @Test
    public void whenGetAllHooks_thenReturnValidJsonOfHooks() throws JsonProcessingException {
        testPermissions.put("admin", "true");
        testConfig.put("https://gepardenblick.apps.play.gepaplexx.com/push", "https://gepardenblick.apps.play.gepaplexx.com/delete");
        OrgsRepo orgsRepo = new OrgsRepo("gepaplexx-demos", "https://api.github.com/orgs/gepaplexx-demos");
        Repository repository = new Repository("gepardenblick", "https://github.com/gepaplexx-demos/gepardenblick", testPermissions);
        SourceHook sourceHook = new SourceHook(Boolean.TRUE, testConfig);
        testFacadeHook.add(sourceHook);
        expectedResponseHooks.put(orgsRepo.login, sourceHook.config);
        Mockito.when(sourceHookService.getHookByRepos(orgsRepo.login, repository.name)).thenReturn(testFacadeHook);

        Assertions.assertEquals(facadeResource.getAllHooks(), mapper.writeValueAsString(expectedResponse));
    }

}
