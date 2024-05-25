package org.example.payment.common;

import java.util.UUID;

public class IdempotencyCreator {
    private IdempotencyCreator() {

    }

    public static String create(String seed) {
        return UUID.nameUUIDFromBytes(seed.getBytes()).toString();
    }
}
