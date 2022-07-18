package com.gepardec.utils;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

@ApplicationScoped
public class RequestAuthorizationArgoCdHeaderFactory implements ClientHeadersFactory {

    @ConfigProperty(name = "argoCDtoken")
    String argoCDtoken;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
        result.add("Accept", "application/json");
        result.add("Authorization", "Bearer " + argoCDtoken);
        return result;

    }

}
