package org.selyu.pando.client.model;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class Rank {
    private UUID uuid;
    private String name;
    private String color;
    private int weight;
    private String prefix;
    private String suffix;

    @NotNull
    public UUID getUuid() {
        return uuid;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getColor() {
        return color;
    }

    @NotNull
    public int getWeight() {
        return weight;
    }

    @NotNull
    public String getPrefix() {
        return prefix;
    }

    @NotNull
    public String getSuffix() {
        return suffix;
    }
}

