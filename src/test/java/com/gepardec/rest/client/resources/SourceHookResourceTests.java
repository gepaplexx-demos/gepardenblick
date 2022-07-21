package com.gepardec.rest.client.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gepardec.rest.client.model.SourceHook;
import com.gepardec.rest.client.services.ISourceHookService;
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

import static java.lang.Boolean.TRUE;

@QuarkusTest
public class SourceHookResourceTests {

    @InjectMock(convertScopes = true)
    @RestClient
    ISourceHookService sourceHookService;

    @Inject
    SourceHookResource sourceHookResource;

    @Inject
    ObjectMapper mapper;

    private HashSet<SourceHook> testHooks;
    private HashMap<String, String> testConfig;
    private HashMap<Boolean, HashMap> expectedResponse;

    @BeforeEach
    public void setup() {
        testHooks = new HashSet<>();
        testConfig = new HashMap<>();
        expectedResponse = new HashMap<>();
    }

    @Test
    public void whenGetHookByOwnerAndRepoGivenRepoAndOwnerName_thenReturnValidJsonOfSingleHook() throws JsonProcessingException {
        testConfig.put("https://gepardenblick.apps.play.gepaplexx.com/push", "https://gepardenblick.apps.play.gepaplexx.com/push");
        SourceHook sourceHook = new SourceHook(TRUE, testConfig);
        testHooks.add(sourceHook);
        expectedResponse.put(sourceHook.active, sourceHook.config);
        Mockito.when(sourceHookService.getHookByOwnerAndRepo("gepaplexx-demos", "gepardenblick")).thenReturn(testHooks);

        Assertions.assertEquals(sourceHookResource.getHookByOwnerAndRepo("testorg", "testorg"), mapper.writeValueAsString(expectedResponse));
    }
}
