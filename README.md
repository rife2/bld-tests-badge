# [Bld](https://github.com/rife2/rife2/wiki/What-Is-Bld) extension for the Tests Badge service

[![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java](https://img.shields.io/badge/java-17%2B-blue)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![bld](https://img.shields.io/badge/2.0.1-FA9052?label=bld&labelColor=2392FF)](https://rife2.com/bld)
[![Release](https://flat.badgen.net/maven/v/metadata-url/https:/repo.rife2.com/releases/com/uwyn/rife2/bld-tests-badge/maven-metadata.xml)](https://repo.rife2.com/#/releases/com/uwyn/rife2/bld-tests-badge)
[![GitHub CI](https://github.com/rife2/bld-tests-badge/actions/workflows/bld.yml/badge.svg)](https://github.com/rife2/bld-tests-badge/actions/workflows/bld.yml)

A `bld` extension for reporting test results through a
[Tests Badge](https://github.com/rife2/tests-badge) service.

The complete documentation of `TestsBadgeOperation` can be found in its [javadocs](https://rife2.github.io/bld-tests-badge/rife/bld/extension/TestsBadgeOperation.html).

This is an example usage where you replace the `test` command with the
`TestsBadgeOperation`. Please note that the `TestBadgeOperation` currently
only works with JUnit 5 tests.

```java
private final TestsBadgeOperation testsBadgeOperation = new TestsBadgeOperation();

public void test()
throws Exception {
    testsBadgeOperation.executeOnce(() -> testsBadgeOperation
        .url(property("testsBadgeUrl"))
        .apiKey(property("testsBadgeApiKey"))
        .fromProject(this));
}
```

The `testsBadgeUrl` and `testsBadgeApiKey` properties can then be passed in
through `bld`'s [hierarchical properties](https://github.com/rife2/rife2/wiki/Bld-Sensitive-and-Common-Data).
Typically, you would set this up through a CI workflow.

Here's how this could look for a `bld.yml` GitHub worflow:

```yaml
name: bld-ci

on: [push, pull_request, workflow_dispatch]

jobs:
  build-project:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout source repository
        uses: actions/checkout@v3
        with:
          fetch-depth: 0

      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          distribution: 'zulu'17${{ matrix.java-version }}

      - name: Run tests
        run: >-
          ./bld download compile test
          -DtestsBadgeUrl=https://rife2.com/tests-badge/update/group/artifact
          -DtestsBadgeApiKey=${{ secrets.TESTS_BADGE_API_KEY }}
```

The `TESTS_BADGE_API_KEY` secret can then be added to the settings of your
GitHub project without it being visible publicly.