package com.gepardec.rest.client.model;

import java.util.HashMap;

public class Repository {

    public String name;
    public String html_url;
    public HashMap<String, String> permissions;

    public Repository(String name, String html_url, HashMap<String, String> permissions) {
        this.name = name;
        this.html_url = html_url;
        this.permissions = permissions;
    }
}


