package com.gepardec.rest.client.hooks;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.UUID;

@ApplicationScoped
public class RequestUUIDHeaderFactory implements ClientHeadersFactory {

    @ConfigProperty(name = "Nico-Strassi-Token")
    String token;
    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
        result.add("X-request-uuid", UUID.randomUUID().toString());
        result.add("Accept:", "application/vnd.github+json");
        result.add("Authorization:", token);
        return result;
    }
}