package org.selyu.pando.client.model;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class Lookup {
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
        return "Lookup{" +
                "uuid=" + uuid +
                ", username='" + username + '\'' +
                '}';
    }
}
