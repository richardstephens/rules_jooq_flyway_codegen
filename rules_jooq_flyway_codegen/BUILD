load("@rules_java//java:defs.bzl", "java_library")

exports_files([
    "jooqflyway.bzl",
])

java_library(
    name = "codegen",
    srcs = glob(["src/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":flyway_compileonly",
        ":jooq_compileonly",
    ],
)

java_library(
    name = "jooq_compileonly",
    neverlink = True,
    visibility = ["//visibility:private"],
    exports = [
        "@rules_jooq_flyway_codegen_maven//:org_jooq_jooq",
        "@rules_jooq_flyway_codegen_maven//:org_jooq_jooq_codegen",
        "@rules_jooq_flyway_codegen_maven//:org_jooq_jooq_meta",
    ],
)

java_library(
    name = "flyway_compileonly",
    neverlink = True,
    visibility = ["//visibility:private"],
    exports = [
        "@rules_jooq_flyway_codegen_maven//:mysql_mysql_connector_java",
        "@rules_jooq_flyway_codegen_maven//:org_flywaydb_flyway_core",
        "@rules_jooq_flyway_codegen_maven//:org_flywaydb_flyway_database_postgresql",
        "@rules_jooq_flyway_codegen_maven//:org_flywaydb_flyway_mysql",
        "@rules_jooq_flyway_codegen_maven//:org_mariadb_jdbc_mariadb_java_client",
        "@rules_jooq_flyway_codegen_maven//:org_postgresql_postgresql",
        "@rules_jooq_flyway_codegen_maven//:org_testcontainers_jdbc",
        "@rules_jooq_flyway_codegen_maven//:org_testcontainers_mariadb",
        "@rules_jooq_flyway_codegen_maven//:org_testcontainers_mysql",
        "@rules_jooq_flyway_codegen_maven//:org_testcontainers_postgresql",
        "@rules_jooq_flyway_codegen_maven//:org_testcontainers_testcontainers",
        "@rules_jooq_flyway_codegen_maven//:org_xerial_sqlite_jdbc",
    ],
)
