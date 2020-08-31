package org.selyu.pando.client.model;

import org.jetbrains.annotations.NotNull;

import static org.selyu.pando.client.util.Preconditions.requireNotNull;

public final class RankEditFieldRequest {
    private final String field;
    private final Object newValue;

    public RankEditFieldRequest(@NotNull String field, @NotNull Object newValue) {
        requireNotNull(field, newValue);
        this.field = field;
        this.newValue = newValue;
    }

    @NotNull
    public String getField() {
        return field;
    }

    @NotNull
    public Object getNewValue() {
        return newValue;
    }
}
