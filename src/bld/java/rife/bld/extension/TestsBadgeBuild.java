package rife.bld.extension;

import rife.bld.Project;
import rife.bld.publish.PublishInfo;

import java.util.List;

import static rife.bld.dependencies.Repository.*;
import static rife.bld.dependencies.Scope.*;
import static rife.bld.operations.JavadocOptions.DocLinkOption.NO_MISSING;

public class TestsBadgeBuild extends Project {
    public TestsBadgeBuild() {
        pkg = "rife.bld.extension";
        name = "TestsBadge";
        version = version(0,9,0);
        javadocOptions.docLint(NO_MISSING);
        publishRepository = repository("rife2");
        publishInfo = new PublishInfo()
            .groupId("com.uwyn.rife2")
            .artifactId("bld-tests-badge")
            .signKey(property("sign.key"))
            .signPassphrase(property("sign.passphrase"));

        javaRelease = 17;
        downloadSources = true;
        autoDownloadPurge = true;
        repositories = List.of(MAVEN_CENTRAL, RIFE2);
        scope(compile)
            .include(dependency("com.uwyn.rife2", "rife2", version(1,5,16)));
    }

    public static void main(String[] args) {
        new TestsBadgeBuild().start(args);
    }
}