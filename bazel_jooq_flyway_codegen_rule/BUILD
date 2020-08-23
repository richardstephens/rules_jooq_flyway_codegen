exports_files([
    "jooqflyway.bzl",
])

java_library(
    name = "codegen",
    #    main_class = "dev.richst.jooq_bazel.JooqBazelCodegen",
    srcs = glob(["src/*.java"]),
    resource_jars = [
    ],
    visibility = ["//visibility:public"],
    runtime_deps = [
    ],
    deps = [
        "@bazel_jooq_flyway_codegen_rule_maven//:mysql_mysql_connector_java",
        "@bazel_jooq_flyway_codegen_rule_maven//:org_flywaydb_flyway_core",
        "@bazel_jooq_flyway_codegen_rule_maven//:org_jooq_jooq",
        "@bazel_jooq_flyway_codegen_rule_maven//:org_jooq_jooq_codegen",
        "@bazel_jooq_flyway_codegen_rule_maven//:org_jooq_jooq_meta",
        "@bazel_jooq_flyway_codegen_rule_maven//:org_mariadb_jdbc_mariadb_java_client",
        "@bazel_jooq_flyway_codegen_rule_maven//:org_postgresql_postgresql",
        "@bazel_jooq_flyway_codegen_rule_maven//:org_testcontainers_jdbc",
        "@bazel_jooq_flyway_codegen_rule_maven//:org_testcontainers_mariadb",
        "@bazel_jooq_flyway_codegen_rule_maven//:org_testcontainers_mysql",
        "@bazel_jooq_flyway_codegen_rule_maven//:org_testcontainers_postgresql",
    ],
)
