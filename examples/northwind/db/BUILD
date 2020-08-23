load("//bazel_jooq_flyway_codegen_rule:jooqflyway.bzl", "jooqflyway")

java_library(
    name = "migration-jar",
    resource_strip_prefix = "examples/northwind/db",
    resources = glob(["db/migration/*.sql"]),
    visibility = ["//examples/northwind:__subpackages__"],
)

jooqflyway(
    name = "northwind-db-classes",
    codegen_xml = "codegen.xml",
    db_type = "mysql",
    jooq_dep = "@bazel_jooq_flyway_codegen_rule_maven//:org_jooq_jooq",
    jooq_meta_dep = "@bazel_jooq_flyway_codegen_rule_maven//:org_jooq_jooq_meta",
    migration_jar = ":migration-jar",
    visibility = ["//examples/northwind:__subpackages__"],
)
