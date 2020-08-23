java_binary(
    name = "northwind",
    srcs = glob(["src/main/java/**/*.java"]),
    main_class = "dev.richst.jooq_bazel_example.northwind.service.App",
    resource_jars = [
        "//examples/northwind/db:migration-jar",
    ],
    resources = [
    ],
    runtime_deps = [
        "@bazel_jooq_flyway_codegen_rule_example_maven//:org_slf4j_slf4j_simple",
    ],
    deps = [
        "//examples/northwind/db:northwind-db-classes",
        "@bazel_jooq_flyway_codegen_rule_example_maven//:com_google_code_gson_gson",
        "@bazel_jooq_flyway_codegen_rule_example_maven//:com_sparkjava_spark_core",
        "@bazel_jooq_flyway_codegen_rule_example_maven//:com_zaxxer_HikariCP",
        "@bazel_jooq_flyway_codegen_rule_example_maven//:mysql_mysql_connector_java",
        "@bazel_jooq_flyway_codegen_rule_example_maven//:org_flywaydb_flyway_core",
        "@bazel_jooq_flyway_codegen_rule_example_maven//:org_jooq_jooq",
        "@bazel_jooq_flyway_codegen_rule_example_maven//:org_jooq_jooq_meta",
        "@bazel_jooq_flyway_codegen_rule_example_maven//:org_testcontainers_mysql",
        "@bazel_jooq_flyway_codegen_rule_example_maven//:org_testcontainers_testcontainers",
    ],
)
