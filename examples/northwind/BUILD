java_binary(
    name = "northwind",
    srcs = glob(["src/main/java/**/*.java"]),
    main_class = "dev.richst.jooq_bazel_example.northwind.service.App",
    resources = [
    ],
    runtime_deps = [
        "//examples/northwind/db:migration-jar",
        "@rules_jooq_flyway_codegen_example_maven//:org_slf4j_slf4j_simple",
    ],
    deps = [
        "//examples/northwind/db:northwind-db-classes",
        "@rules_jooq_flyway_codegen_example_maven//:com_google_code_gson_gson",
        "@rules_jooq_flyway_codegen_example_maven//:com_sparkjava_spark_core",
        "@rules_jooq_flyway_codegen_example_maven//:com_zaxxer_HikariCP",
        "@rules_jooq_flyway_codegen_example_maven//:mysql_mysql_connector_java",
        "@rules_jooq_flyway_codegen_example_maven//:org_flywaydb_flyway_core",
        "@rules_jooq_flyway_codegen_example_maven//:org_jooq_jooq",
        "@rules_jooq_flyway_codegen_example_maven//:org_jooq_jooq_meta",
        "@rules_jooq_flyway_codegen_example_maven//:org_testcontainers_mysql",
        "@rules_jooq_flyway_codegen_example_maven//:org_testcontainers_testcontainers",
    ],
)
