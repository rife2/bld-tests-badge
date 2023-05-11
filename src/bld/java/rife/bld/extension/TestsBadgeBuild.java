package rife.bld.extension;

import rife.bld.Project;
import rife.bld.publish.PublishDeveloper;
import rife.bld.publish.PublishInfo;
import rife.bld.publish.PublishLicense;
import rife.bld.publish.PublishScm;

import java.util.List;

import static rife.bld.dependencies.Repository.*;
import static rife.bld.dependencies.Scope.*;
import static rife.bld.operations.JavadocOptions.DocLinkOption.NO_MISSING;

public class TestsBadgeBuild extends Project {
    public TestsBadgeBuild() {
        pkg = "rife.bld.extension";
        name = "TestsBadge";
        version = version(1,4,0,"SNAPSHOT");
        archiveBaseName = "bld-tests-badge";

        javaRelease = 17;
        downloadSources = true;
        autoDownloadPurge = true;

        repositories = List.of(MAVEN_CENTRAL, RIFE2_RELEASES, SONATYPE_SNAPSHOTS);
        scope(compile)
            .include(dependency("com.uwyn.rife2", "bld", version(1,7,0,"SNAPSHOT")));
        scope(test)
            .include(dependency("org.junit.jupiter", "junit-jupiter", version(5,9,3)))
            .include(dependency("org.junit.platform", "junit-platform-console-standalone", version(1,9,3)));

        javadocOperation()
            .javadocOptions()
            .docLint(NO_MISSING)
            .link("https://rife2.github.io/rife2/");

        publishOperation()
            .repository(version.isSnapshot() ? repository("rife2-snapshots") : repository("rife2-releases"))
            .info()
                .groupId("com.uwyn.rife2")
                .artifactId("bld-tests-badge")
                .description("bld extension for reporting test results through a Tests Badge service")
                .url("https://github.com/rife2/bld-tests-badge")
                .developer(new PublishDeveloper()
                    .id("gbevin")
                    .name("Geert Bevin")
                    .email("gbevin@uwyn.com")
                    .url("https://github.com/gbevin"))
                .license(new PublishLicense()
                    .name("The Apache License, Version 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.txt"))
                .scm(new PublishScm()
                    .connection("scm:git:https://github.com/rife2/bld-tests-badge.git")
                    .developerConnection("scm:git:git@github.com:rife2/bld-tests-badge.git")
                    .url("https://github.com/rife2/bld-tests-badge"))
                .signKey(property("sign.key"))
                .signPassphrase(property("sign.passphrase"));
    }

    public static void main(String[] args) {
        new TestsBadgeBuild().start(args);
    }
}