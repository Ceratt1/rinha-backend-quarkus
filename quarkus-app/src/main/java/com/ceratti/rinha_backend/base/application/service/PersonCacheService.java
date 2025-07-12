package com.ceratti.rinha_backend.base.application.service;

import io.vertx.mutiny.redis.client.Command;
import io.vertx.mutiny.redis.client.Redis;
import io.vertx.mutiny.redis.client.Request;
import io.vertx.mutiny.redis.client.Response;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.smallrye.mutiny.Uni;

@Singleton
public class PersonCacheService {

    @Inject
    Redis redisClient;

    public Uni<String> set(String key, String value) {
        Request request = Request.cmd(Command.SET).arg(key).arg(value);
        return redisClient.send(request)
                .onItem().transform(Response::toString);
    }

    public Uni<String> get(String key) {
        Request request = Request.cmd(Command.GET).arg(key);
        return redisClient.send(request)
                .onItem().transform(resp -> resp != null ? resp.toString() : null);
    }
}
