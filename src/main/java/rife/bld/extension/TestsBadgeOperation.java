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
    private String url_;
    private String apiKey_;

    /**
     * Creates a new operation.
     *
     * @since 1.0
     */
    public TestsBadgeOperation() {
    }

    /**
     * Set the URL for the operation.
     *
     * @param url the TestsBadge service update API URL
     * @return this operation instance
     * @since 1.0
     */
    public TestsBadgeOperation url(String url) {
        url_ = url;
        return this;
    }

    /**
     * Set the API key for the operation.
     *
     * @param apiKey the unique API key for your project
     * @return this operation instance
     * @since 1.0
     */
    public TestsBadgeOperation apiKey(String apiKey) {
        apiKey_ = apiKey;
        return this;
    }

    /**
     * Retrieves the URL for the operation.
     *
     * @return the TestsBadge service update API URL
     * @since 1.0
     */
    public String url() {
        return url_;
    }

    /**
     * Retrieves the API key for the operation.
     *
     * @return the unique API key for your project
     * @since 1.0
     */
    public String apiKey() {
        return apiKey_;
    }

    private final static Pattern PASSED_PATTERN = Pattern.compile("(\\d+) tests successful");
    private final static Pattern SKIPPED_PATTERN = Pattern.compile("(\\d+) tests skipped");
    private final static Pattern FAILED_PATTERN = Pattern.compile("(\\d+) tests failed");

    private Integer passed_ = null;
    private Integer skipped_ = null;
    private Integer failed_ = null;

    public Function<String, Boolean> outputProcessor() {
        return s -> {
            System.out.println(s);

            if (url() != null && apiKey() != null && s.startsWith("[")) {
                var passed_matcher = PASSED_PATTERN.matcher(s);
                var skipped_matcher = SKIPPED_PATTERN.matcher(s);
                var failed_matcher = FAILED_PATTERN.matcher(s);
                if (passed_matcher.find()) {
                    passed_ = Integer.parseInt(passed_matcher.group(1));
                } else if (skipped_matcher.find()) {
                    skipped_ = Integer.parseInt(skipped_matcher.group(1));
                } else if (failed_matcher.find()) {
                    failed_ = Integer.parseInt(failed_matcher.group(1));
                }

                if (passed_ != null && skipped_ != null && failed_ != null) {
                    try {
                        var response = HttpClient.newHttpClient()
                            .send(HttpRequest.newBuilder().uri(new URI(
                                    url_ + "?" +
                                        "apiKey=" + apiKey_ +
                                        "&passed=" + passed_ +
                                        "&failed=" + failed_ +
                                        "&skipped=" + skipped_))
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