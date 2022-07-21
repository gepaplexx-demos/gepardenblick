package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.HashMap;

@QuarkusTest
public class ArgoCdRepoResourceTests {

    @InjectMock
    @RestClient
    IArgoCdRepoService argoCdRepoService;

    @Inject
    ArgoCdRepoResource argoCdRepoResource;

    @Inject
    ObjectMapper mapper;

    private HashMap<String, String> expectedResponse;

    @BeforeEach
    public void setup() {
        expectedResponse = new HashMap<>();
    }

    @Test
    public void whenGetAllReposFromArgoCD_thenReturnValidJsonOfSingleRepo() throws JsonProcessingException {
        Items testItem = new Items("gepardenblick" , "git@github.com:gepaplexx-demos/gepardenblick-ci");
        expectedResponse.put(testItem.name, testItem.repoURL);

        String argoJsonRes = "{\"items\":[{\"metadata\":{\"name\":\"gepardenblick\",\"namespace\":\"gepaplexx-cicd-tools\"},\"spec\":{\"source\":{\"repoURL\":\"git@github.com:gepaplexx-demos/gepardenblick-ci\",\"path\":\".\",\"targetRevision\":\"main\"}}}]}";
        Mockito.when(argoCdRepoService.getAllRepos()).thenReturn(argoJsonRes);

        Assertions.assertEquals(argoCdRepoResource.getAllRepos(), mapper.writeValueAsString(expectedResponse));
    }
}
