package com.ossrep.ercot;

import java.text.MessageFormat;

public class ScraperException extends RuntimeException {

    public ScraperException(String message, Object... objects) {
        super(String.format(message, objects));
    }

    public ScraperException(String message, Throwable cause) {
        super(message, cause);
    }

}
