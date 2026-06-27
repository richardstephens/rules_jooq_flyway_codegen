load("@rules_java//java:defs.bzl", "java_library")
load("//rules_jooq_flyway_codegen:jooqflyway.bzl", "jooqflyway")

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
    generator_deps = [
        "@rules_jooq_flyway_codegen_example_maven//:com_google_code_gson_gson",
        "@rules_jooq_flyway_codegen_example_maven//:com_sparkjava_spark_core",
        "@rules_jooq_flyway_codegen_example_maven//:com_zaxxer_HikariCP",
        "@rules_jooq_flyway_codegen_example_maven//:mysql_mysql_connector_java",
        "@rules_jooq_flyway_codegen_example_maven//:org_flywaydb_flyway_core",
        "@rules_jooq_flyway_codegen_example_maven//:org_flywaydb_flyway_mysql",
        "@rules_jooq_flyway_codegen_example_maven//:org_jooq_jooq_codegen",
        "@rules_jooq_flyway_codegen_example_maven//:org_jooq_jooq",
        "@rules_jooq_flyway_codegen_example_maven//:org_jooq_jooq_meta",
        "@rules_jooq_flyway_codegen_example_maven//:org_testcontainers_mysql",
        "@rules_jooq_flyway_codegen_example_maven//:org_testcontainers_testcontainers",
    ],
    library_deps = [
        "@rules_jooq_flyway_codegen_example_maven//:org_jooq_jooq",
        "@rules_jooq_flyway_codegen_example_maven//:org_jooq_jooq_meta",
    ],
    migration_jar = ":migration-jar",
    visibility = ["//examples/northwind:__subpackages__"],
)
