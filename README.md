# [Bld](https://github.com/rife2/rife2/wiki/What-Is-Bld) extension for the TestsBadge service

[![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java](https://img.shields.io/badge/java-17%2B-blue)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Release](https://flat.badgen.net/maven/v/metadata-url/https:/repo.rife2.com/releases/com/uwyn/rife2/bld-tests-badge/maven-metadata.xml)](https://repo.rife2.com/#/releases/com/uwyn/rife2/bld-tests-badge)
[![GitHub CI](https://github.com/rife2/bld-tests-badge/actions/workflows/bld.yml/badge.svg)](https://github.com/rife2/bld-tests-badge/actions/workflows/bld.yml)

A `bld` extension for reporting test results through a
[Test Badge](https://github.com/rife2/tests-badge) service.

The complete documentation of `TestsBadgeOperation` can be found in its [javadocs](https://rife2.github.io/bld-tests-badge/rife/bld/extension/TestsBadgeOperation.html).

This is an example usage where you replace the `test` command with the
`TestsBadgeOperation`.

```java
@BuildCommand
public void test()
throws Exception {
    new TestsBadgeOperation()
        .url(property("testsBadgeUrl"))
        .apiKey(property("testsBadgeApiKey"))
        .fromProject(this)
        .execute();
}
```