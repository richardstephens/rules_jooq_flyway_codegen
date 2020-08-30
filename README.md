Bazel rules for jOOQ codegen from Flyway migrations
===================================================

This Bazel rule will apply Flyway migrations to a database
launched in a Testcontainer, use those to run jOOQ's code
generator, and produce a source JAR containing your generated
classes.

Please note that this rule is still in an alpha-quality state,
and the steps taken to import it may change in the future.

To import the rules:

    load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
    http_archive(
        name = "rules_jooq_flyway_codegen",
        urls = ["https://github.com/richardstephens/rules_jooq_flyway_codegen/releases/download/v0.2/rules_jooq_flyway_codegen-v0.2.tgz"],
        sha256 = "a94f95319366a1f1611ada55b72cfabcad328cc628f6d091ae3d0a5f3bde8108",
    )

You must also define a maven install for the codegen's dependencies:
(Note that these dependencies are only for the code generator, they are not
propagated to anything that imports the generated classes)

    load("@rules_jvm_external//:defs.bzl", rules_jooq_flyway_codegen_maven_install = "maven_install")
    rules_jooq_flyway_codegen_maven_install(
        name = "rules_jooq_flyway_codegen_maven",
        artifacts = [
            "org.flywaydb:flyway-core:6.4.4",
            "org.jooq:jooq:3.13.2",
            "org.jooq:jooq-meta:3.13.2",
            "org.jooq:jooq-codegen:3.13.2",
            "org.testcontainers:testcontainers:1.14.3",
            "org.testcontainers:postgresql:1.14.3",
            "org.testcontainers:mariadb:1.14.3",
            "org.testcontainers:mysql:1.14.3",
            "org.postgresql:postgresql:42.2.14",
            "org.mariadb.jdbc:mariadb-java-client:2.6.2",
            "mysql:mysql-connector-java:8.0.21",
        ],
        fetch_sources = True,
        repositories = [
            "https://repo1.maven.org/maven2",
        ],
    )

The codegen rule takes as a parameter a resource jar containing your
application's flyway migrations. Your directory structure should look
something like this:

    src/
      myservice/
        db/
          db/
            migration/
              V01_00_00__first_migration.sql
          codegen.xml
          BUILD

Note the double-nested db folders. This is needed because Flyway expects to find its migrations at
a path in the form of `db/migration` by default. We might try to change how this works 
or make it customisable in the future.
 
In the BUILD file, you can build a resource jar with your migrations as follows:
    
    java_library(
        name = "migration-jar",
        resource_strip_prefix = "src/myservice/db",
        resources = glob(["db/migration/*.sql"]),
        visibility = ["//src/myservice:__subpackages__"],
    )

Note the `resource_strip_prefix`. That should be the path to this BUILD file

Now you need to set up your `codegen.xml` file. This will be passed to jOOQ's code
generator as is, with the exception of overriding the output directory you specify
to a temporary directory for the generated classes. An [example codgen.xml file is here](./examples/northwind/db/codegen.xml),
and the [jOOQ documentation for the codegen.xml file is here](https://www.jooq.org/doc/latest/manual/code-generation/codegen-configuration/). 

Now that you have a resource jar containing your migrations, you can call the
code generator like so:

    load("@rules_jooq_flyway_codegen//rules_jooq_flyway_codegen:jooqflyway.bzl", "jooqflyway")
    jooqflyway(
        name = "myservice-db-classes",
        codegen_xml = "codegen.xml",
        db_type = "mysql",
        jooq_dep = "@maven//:org_jooq_jooq",
        jooq_meta_dep = "@maven//:org_jooq_jooq_meta",
        migration_jar = ":migration-jar",
        visibility = ["//src/myservice:__subpackages__"],
    )

Valid options for db type are `postgres`, `mariadb`, or `mysql` at present, but more
will be supported in the future.

And that's it! You can now depend on `//src/myservice/db:myservice-db-classes`
