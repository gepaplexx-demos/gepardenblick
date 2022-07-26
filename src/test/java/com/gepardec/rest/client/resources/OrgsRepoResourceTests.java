package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.OrgsRepo;
import com.gepardec.rest.client.services.IOrgsRepoService;
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
public class OrgsRepoResourceTests {
    @InjectMock
    @RestClient
    IOrgsRepoService orgsRepoService;

    @Inject
    OrgsRepoResource orgsRepoResource;

    @Inject
    ObjectMapper mapper;

    private HashSet<OrgsRepo> testOrg;
    private HashMap<String, String> expectedResponse;

    @BeforeEach
    public void setup() {
        testOrg = new HashSet<>();
        expectedResponse = new HashMap<>();
    }

    @Test
    public void whenGetOrgsByToken_thenReturnValidJsonOfSingleOrgRepo() throws JsonProcessingException {
        OrgsRepo orgsRepo = new OrgsRepo("gepaplexx-demos", "https://api.github.com/orgs/gepaplexx-demos");
        testOrg.add(orgsRepo);
        expectedResponse.put(orgsRepo.login, orgsRepo.url);
        Mockito.when(orgsRepoService.getOrgByToken()).thenReturn(testOrg);

        Assertions.assertEquals(orgsRepoResource.getOrgByToken(), mapper.writeValueAsString(expectedResponse));
    }
}
