package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.ArgoCdRepo;
import com.gepardec.rest.client.modelAtribute.Items;
import com.gepardec.rest.client.services.IArgoCdRepoService;
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
import java.util.HashSet;
import java.util.List;

@QuarkusTest
public class ArgoCdRepoResourceTests {

    private final String TEST_ORG = "testorg";

    @InjectMock(convertScopes = true)
    @RestClient
    IArgoCdRepoService argoCdRepoService;

    @Inject
    ArgoCdRepoResource argoCdRepoResource;

    @Inject
    ObjectMapper mapper;

    private HashSet<ArgoCdRepo> testArgo;
    private HashMap<String, String> expectedResponse;

    Items testItem = new Items("gepardenblick" , "git@github.com:gepaplexx-demos/gepardenblick-ci");
    List<Items> testItemsList;

    @BeforeEach
    public void setup() {
        testArgo = new HashSet<>();
        expectedResponse = new HashMap<>();
        testItemsList = new ArrayList<>();
    }

    @Test
    public void whenGetArgoCD_thenReturnValidJsonOfSingleArgoCD() throws JsonProcessingException {
        testItemsList.add(testItem);
        ArgoCdRepo argoCdRepo = new ArgoCdRepo(testItemsList);
        testArgo.add(argoCdRepo);
        expectedResponse.put(testItem.name, testItem.repoURL);
        Mockito.when(argoCdRepoService.getAllRepos()).thenReturn(String.valueOf(testArgo));

        Assertions.assertEquals(argoCdRepoResource.getAllRepos(), mapper.writeValueAsString(expectedResponse));
    }
}
