package org.selyu.pando.client.model;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class User {
    private UUID uuid;
    private String username;

    @NotNull
    public UUID getUuid() {
        return uuid;
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", username='" + username + '\'' +
                '}';
    }
}
