package com.ceratti.rinha_backend.base.infra.controllers;

import com.ceratti.rinha_backend.base.application.service.PersonCacheService;
import com.ceratti.rinha_backend.base.domain.models.PersonCache;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;


@Path("/person")
public class PersonController {
    
    @Inject
    PersonCacheService service;


    @GET
    @Path("/{key}")
    public String get(@PathParam("key") String key) {
        return service.get(key).await().indefinitely().toString();
    }

    @POST
    @Path("/test")
    public String post(PersonCache person) {
        var test = service.set(person.getKey(), person.getValue());
        return test.await().indefinitely().toString();
    }


}

