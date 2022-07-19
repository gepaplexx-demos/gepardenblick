package com.gepardec.rest.client.modelAtribute;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Items {

    public String repoURL;

    public String name;

    @JsonProperty("spec")
    @SuppressWarnings("unchecked")
    private void unpackRepoUrlFromNestedObject(Map<String, Object> spec) {
        Map<String,String> source = (Map<String,String>)spec.get("source");
        this.repoURL = source.get("repoURL");
    }

    @JsonProperty("metadata")
    private void unpackNameFromNestedObject(Map<String, Object> metadata) {
        this.name = (String)metadata.get("name");
    }
}
