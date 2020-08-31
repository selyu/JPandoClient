package org.selyu.pando.client.model;

import org.jetbrains.annotations.NotNull;

import static org.selyu.pando.client.util.Preconditions.requireNotNull;

public final class RankCreateRequest {
    private final String name;

    public RankCreateRequest(@NotNull String name) {
        requireNotNull(name);
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }
}
