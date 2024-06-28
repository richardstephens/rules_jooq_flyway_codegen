workspace(name = "rules_jooq_flyway_codegen")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "3.0"

RULES_JVM_EXTERNAL_SHA = "62133c125bf4109dfd9d2af64830208356ce4ef8b165a6ef15bbff7460b35c3a"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", rules_jooq_flyway_codegen_maven_install = "maven_install")

TESTCONTAINERS_VER = "1.19.8"

JOOQ_VER = "3.19.10"

FLYWAY_VER = "10.15.0"

rules_jooq_flyway_codegen_maven_install(
    name = "rules_jooq_flyway_codegen_maven",
    artifacts = [
        "org.flywaydb:flyway-core:%s" % FLYWAY_VER,
        "org.flywaydb:flyway-database-postgresql:%s" % FLYWAY_VER,
        "org.flywaydb:flyway-mysql:%s" % FLYWAY_VER,
        "org.jooq:jooq:%s" % JOOQ_VER,
        "org.jooq:jooq-meta:%s" % JOOQ_VER,
        "org.jooq:jooq-codegen:%s" % JOOQ_VER,
        "org.testcontainers:testcontainers:%s" % TESTCONTAINERS_VER,
        "org.testcontainers:postgresql:%s" % TESTCONTAINERS_VER,
        "org.testcontainers:mariadb:%s" % TESTCONTAINERS_VER,
        "org.testcontainers:mysql:%s" % TESTCONTAINERS_VER,
        "org.postgresql:postgresql:42.2.14",
        "org.mariadb.jdbc:mariadb-java-client:2.6.2",
        "mysql:mysql-connector-java:8.0.21",
        "org.xerial:sqlite-jdbc:3.32.3.2",
    ],
    fetch_sources = True,
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)

load("@rules_jvm_external//:defs.bzl", rules_jooq_flyway_codegen_example_maven_install = "maven_install")

rules_jooq_flyway_codegen_example_maven_install(
    name = "rules_jooq_flyway_codegen_example_maven",
    artifacts = [
        "org.flywaydb:flyway-core:%s" % FLYWAY_VER,
        "org.flywaydb:flyway-database-postgresql:%s" % FLYWAY_VER,
        "org.flywaydb:flyway-mysql:%s" % FLYWAY_VER,
        "org.jooq:jooq:%s" % JOOQ_VER,
        "org.jooq:jooq-meta:%s" % JOOQ_VER,
        "org.jooq:jooq-codegen:%s" % JOOQ_VER,
        "org.testcontainers:testcontainers:%s" % TESTCONTAINERS_VER,
        "org.testcontainers:mysql:%s" % TESTCONTAINERS_VER,
        "mysql:mysql-connector-java:8.0.21",
        "com.zaxxer:HikariCP:3.4.5",
        "mysql:mysql-connector-java:8.0.21",
        "com.google.code.gson:gson:2.8.6",
        "com.sparkjava:spark-core:2.9.1",
        "org.slf4j:slf4j-simple:1.7.30",
        "org.xerial:sqlite-jdbc:3.32.3.2",
    ],
    fetch_sources = True,
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)
