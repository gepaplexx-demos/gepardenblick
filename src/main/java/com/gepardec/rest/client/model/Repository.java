package com.gepardec.rest.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Repository {

    private String name;

    @JsonProperty("hooks_url")
    private String hooksURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHooksURL() {
        return hooksURL;
    }

    public void setHooksURL(String hooksURL) {
        this.hooksURL = hooksURL;
    }
}

