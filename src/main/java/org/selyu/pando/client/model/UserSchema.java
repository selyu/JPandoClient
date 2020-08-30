package org.selyu.pando.client.model;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static org.selyu.pando.client.util.Preconditions.requireNotNull;

public final class UserSchema {
    private final UUID uuid;
    private final String username;

    public UserSchema(@NotNull UUID uuid, @NotNull String username) {
        requireNotNull(uuid, username);
        this.uuid = uuid;
        this.username = username;
    }

    @NotNull
    public UUID getUuid() {
        return uuid;
    }

    @NotNull
    public String getUsername() {
        return username;
    }
}