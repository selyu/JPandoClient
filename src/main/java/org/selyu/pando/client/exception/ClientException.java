package org.selyu.pando.client.exception;

import org.jetbrains.annotations.NotNull;

public final class ClientException extends RuntimeException {
    public ClientException(@NotNull String message, @NotNull Throwable throwable) {
        super(message, throwable);
    }

    public ClientException(@NotNull String message) {
        super(message);
    }

    public ClientException() {
        super();
    }
}
