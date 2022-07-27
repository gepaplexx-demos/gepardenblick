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
import java.util.*;

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
    private HashSet<Repository> testFacadeRepoHooks;

    private HashSet<OrgsRepo> testFacadeOrg;
    private HashMap<String, String> testConfig;
    private HashMap<String, String> testPermissions;
    private HashMap<String,  List<String>> expectedResponseRepo;
    private HashMap<String,  List<String>> expectedResponseHooks;



    @BeforeEach
    public void setup() {
        testFacadeHook = new ArrayList<>();
        testFacadeRepo = new ArrayList<>();
        testFacadeRepoHooks = new HashSet<>();
        testFacadeOrg = new HashSet<>();
        testConfig = new HashMap<>();
        expectedResponseHooks = new HashMap<>();
        testPermissions = new HashMap<>();
        expectedResponseRepo = new HashMap<>();
    }

    @Test
    public void whenGetHooksByRepos_thenReturnValidJsonOfHooks() throws JsonProcessingException {
        testPermissions.put("admin", "true");
        testConfig.put("url", "https://gepardenblick.apps.play.gepaplexx.com/push");
        OrgsRepo orgsRepo = new OrgsRepo("gepaplexx-demos", "https://api.github.com/orgs/gepaplexx-demos");
        Repository repository = new Repository("gepardenblick", "https://github.com/gepaplexx-demos/gepardenblick", testPermissions);
        SourceHook sourceHook = new SourceHook(Boolean.TRUE, testConfig);
        testFacadeHook.add(sourceHook);
        testFacadeRepoHooks.add(repository);
        testFacadeOrg.add(orgsRepo);
        expectedResponseHooks.put(repository.name, Collections.singletonList(sourceHook.config.get("url")));
        Mockito.when(repositoryService.getReposByOrg(orgsRepo.login)).thenReturn(testFacadeRepoHooks);
        Mockito.when(sourceHookService.getHookByRepos(orgsRepo.login, repository.name)).thenReturn(testFacadeHook);
        Mockito.when(orgsRepoService.getOrgByToken()).thenReturn(testFacadeOrg);

        Assertions.assertEquals(facadeResource.getHooksByOrg(orgsRepo.login), mapper.writeValueAsString(expectedResponseHooks));
    }

    @Test
    public void whenGetReposFromOrgs_thenReturnValidJsonOfRepos() throws JsonProcessingException {
        testPermissions.put("admin", "true");
        OrgsRepo orgsRepo = new OrgsRepo("gepaplexx-demos", "https://api.github.com/orgs/gepaplexx-demos");
        Repository repository = new Repository("gepardenblick", "https://github.com/gepaplexx-demos/gepardenblick", testPermissions);
        testFacadeRepo.add(repository);
        testFacadeOrg.add(orgsRepo);
        expectedResponseRepo.put(orgsRepo.login, Collections.singletonList(repository.name));
        Mockito.when(repositoryService.getReposByOrgs(orgsRepo.login)).thenReturn(testFacadeRepo);
        Mockito.when(orgsRepoService.getOrgByToken()).thenReturn(testFacadeOrg);

        Assertions.assertEquals(facadeResource.getReposFromOrgs(), mapper.writeValueAsString(expectedResponseRepo));
    }

    @Test
    public void whenGetAllHooks_thenReturnValidJsonOfHooks() throws JsonProcessingException {
        testPermissions.put("admin", "true");
        testConfig.put("url", "https://gepardenblick.apps.play.gepaplexx.com/push");
        OrgsRepo orgsRepo = new OrgsRepo("gepaplexx-demos", "https://api.github.com/orgs/gepaplexx-demos");
        Repository repository = new Repository("gepardenblick", "https://github.com/gepaplexx-demos/gepardenblick", testPermissions);
        SourceHook sourceHook = new SourceHook(Boolean.TRUE, testConfig);
        testFacadeHook.add(sourceHook);
        testFacadeRepoHooks.add(repository);
        testFacadeOrg.add(orgsRepo);
        expectedResponseHooks.put(orgsRepo.login, Collections.singletonList(sourceHook.config.get("url")));
        Mockito.when(sourceHookService.getHookByRepos(orgsRepo.login, repository.name)).thenReturn(testFacadeHook);
        Mockito.when(repositoryService.getReposByOrg(orgsRepo.login)).thenReturn(testFacadeRepoHooks);
        Mockito.when(orgsRepoService.getOrgByToken()).thenReturn(testFacadeOrg);

        Assertions.assertEquals(facadeResource.getAllHooks(), mapper.writeValueAsString(expectedResponseHooks));
    }

}
