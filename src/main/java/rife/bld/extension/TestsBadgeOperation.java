/*
 * Copyright 2001-2023 Geert Bevin (gbevin[remove] at uwyn dot com)
 * Licensed under the Apache License, Version 2.0 (the "License")
 */
package rife.bld.extension;

import rife.bld.operations.TestOperation;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Extends the standard {@code TestOperation} and calls the TestsBadge service
 * with the credentials of your project and the test results.
 * <p>
 * Note that this will parse the resulting output of the test operation, which
 * relies on JUnit 5's output.
 *
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @since 1.0
 */
public class TestsBadgeOperation extends TestOperation {
    private final String url_;
    private final String apiKey_;

    /**
     * Creates a new operation with your credentials.
     *
     * @param url the TestsBadge service update API url
     * @param apiKey the unique API key for your project
     * @since 1.0
     */
    public TestsBadgeOperation(String url, String apiKey) {
        url_ = url;
        apiKey_ = apiKey;
    }

    public Function<String, Boolean> outputProcessor() {
        return s -> {
            System.out.println(s);

            if (apiKey_ != null) {
                var matcher = Pattern.compile(
                    "(\\d+) tests skipped.*(\\d+) tests successful.*(\\d+) tests failed",
                    Pattern.MULTILINE | Pattern.DOTALL).matcher(s);
                if (matcher.find()) {
                    var skipped = Integer.parseInt(matcher.group(1));
                    var passed = Integer.parseInt(matcher.group(2));
                    var failed = Integer.parseInt(matcher.group(3));
                    try {
                        var response = HttpClient.newHttpClient()
                            .send(HttpRequest.newBuilder().uri(new URI(
                                    url_ + "?" +
                                        "apiKey=" + apiKey_ +
                                        "&passed=" + passed +
                                        "&failed=" + failed +
                                        "&skipped=" + skipped))
                                .POST(HttpRequest.BodyPublishers.noBody())
                                .build(), HttpResponse.BodyHandlers.ofString()
                            );
                        System.out.println("RESPONSE: " + response.statusCode());
                        System.out.println(response.body());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            return true;
        };
    }
}