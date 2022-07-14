package com.gepardec.rest.client.model;

import java.util.HashMap;
import java.util.List;

public class ArgoCdRepo {

    public HashMap<String, List<String>> metadata;
    public HashMap<String, List<String>> spec;
    public HashMap<List<String>, List<String>> items;
}
