package com.gepardec.rest.client.model;

import java.util.HashMap;

public class SourceHook {;
    public Boolean active;
    public HashMap<String, String> config;

    public SourceHook(Boolean active, HashMap<String, String> config) {
        this.active = active;
        this.config = config;
    }
}



