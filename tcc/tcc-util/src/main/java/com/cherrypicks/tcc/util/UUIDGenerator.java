package com.cherrypicks.tcc.util;

import java.util.UUID;

public final class UUIDGenerator {

    private UUIDGenerator() {
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
