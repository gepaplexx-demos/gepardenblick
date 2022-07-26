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

    @InjectMock
    @RestClient
    ISourceHookService sourceHookService;

    @Inject
    SourceHookResource sourceHookResource;

    @Inject
    ObjectMapper mapper;

    private HashSet<SourceHook> testHooks;
    private HashMap<String, String> testConfig;
    private HashMap<String, Boolean> expectedResponse;

    @BeforeEach
    public void setup() {
        testHooks = new HashSet<>();
        testConfig = new HashMap<>();
        expectedResponse = new HashMap<>();
    }

    @Test
    public void whenGetHookByOwnerAndRepoGivenRepoAndOwnerName_thenReturnValidJsonOfSingleHook() throws JsonProcessingException {
        testConfig.put("url", "https://gepardenblick.apps.play.gepaplexx.com/push");
        SourceHook sourceHook = new SourceHook(TRUE, testConfig);
        testHooks.add(sourceHook);
        expectedResponse.put(sourceHook.config.get("url"), sourceHook.active);
        Mockito.when(sourceHookService.getHookByOwnerAndRepo("gepaplexx-demos", "gepardenblick")).thenReturn(testHooks);

        Assertions.assertEquals(sourceHookResource.getHookByOwnerAndRepo("gepaplexx-demos", "gepardenblick"), mapper.writeValueAsString(expectedResponse));
    }
}
