exports_files([
    "jooqflyway.bzl",
])

genrule(
    name = "codegen_srcjar",
    srcs = glob(["src/**/*.java"]),
    tools = ["@bazel_tools//tools/zip:zipper"],
    outs = ["jooqflyway_srcjar.srcjar"],
    cmd = "$(location @bazel_tools//tools/zip:zipper) c $@ $(SRCS)",
    visibility = ["//visibility:public"],
)

java_library(
    name = "codegen",
    #    main_class = "dev.richst.jooq_bazel.JooqBazelCodegen",
    srcs = glob(["src/**/*.java"]),
    resource_jars = [
    ],
    visibility = ["//visibility:public"],
    runtime_deps = [
    ],
    deps = [
        "@rules_jooq_flyway_codegen_maven//:mysql_mysql_connector_java",
        "@rules_jooq_flyway_codegen_maven//:org_flywaydb_flyway_core",
        "@rules_jooq_flyway_codegen_maven//:org_jooq_jooq",
        "@rules_jooq_flyway_codegen_maven//:org_jooq_jooq_codegen",
        "@rules_jooq_flyway_codegen_maven//:org_jooq_jooq_meta",
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
