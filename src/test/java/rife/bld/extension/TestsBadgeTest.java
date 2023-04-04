/*
 * Copyright 2001-2023 Geert Bevin (gbevin[remove] at uwyn dot com)
 * Licensed under the Apache License, Version 2.0 (the "License")
 */
package rife.bld.extension;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestsBadgeTest {
    @Test
    void testInstantiation() {
        var operation = new TestsBadgeOperation();
        assertNull(operation.url());
        assertNull(operation.apiKey());
    }

    @Test
    void testPopulation() {
        var operation = new TestsBadgeOperation()
            .url("myurl")
            .apiKey("myapi");
        assertEquals("myurl", operation.url());
        assertEquals("myapi", operation.apiKey());
    }
}
