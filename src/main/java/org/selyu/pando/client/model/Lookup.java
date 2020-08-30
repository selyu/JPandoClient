package org.selyu.pando.client.model;

import java.util.UUID;

public final class Lookup {
    private UUID uuid;
    private String username;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Lookup{" +
                "uuid=" + uuid +
                ", username='" + username + '\'' +
                '}';
    }
}
