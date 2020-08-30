package org.selyu.pando.client.util;

import java.util.Objects;

public final class Preconditions {
    private Preconditions() {
        throw new RuntimeException();
    }

    public static void requireNotNull(Object... objects) {
        for (Object object : objects) {
            if(object == null) {
                throw new NullPointerException(Objects.toString(object));
            }
        }
    }

    public static void validateState(boolean expression) {
        if(!expression) {
            throw new IllegalStateException();
        }
    }
}
