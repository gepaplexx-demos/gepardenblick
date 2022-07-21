package com.gepardec.rest.client.model;

import com.gepardec.rest.client.modelAtribute.Items;

import java.util.List;

public class ArgoCdRepo {

    public List<Items> items;

    public ArgoCdRepo(List<Items> items) {
        this.items = items;
    }
}
