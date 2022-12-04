def _impl(ctx):
    file = ctx.actions.declare_file(ctx.attr.name + ".srcjar")
    args = ctx.actions.args()
    args.add(file.path)
    args.add(ctx.attr.db_type)
    args.add(ctx.attr.docker_image)
    args.add_all(ctx.attr.codegen_xml.files)

    ctx.actions.run(
        inputs = ctx.attr.migration_jar.files.to_list() + ctx.attr.codegen_xml.files.to_list(),
        outputs = [file],
        #outputs = [ctx.outputs],
        executable = ctx.executable.tool,
        arguments = [args],
        use_default_shell_env = True,
    )

    return [DefaultInfo(files = depset([file]))]

jooqflyway_gensrcs = rule(
    implementation = _impl,
    attrs = {
        "migration_jar": attr.label(),
        "codegen_xml": attr.label(allow_single_file = True),
        "tool": attr.label(
            executable = True,
            cfg = "host",
        ),
        "db_type": attr.string(),
        "docker_image": attr.string(),
    },
    fragments = ["jvm"],
    host_fragments = ["jvm"],
)

def jooqflyway(
        name,
        migration_jar,
        visibility,
        codegen_xml,
        db_type,
        jooq_dep = "@maven//:org_jooq_jooq",
        jooq_meta_dep = "@maven//:org_jooq_jooq_meta",
        docker_image = "--",
        maven_install_target = None,
        **kwargs):
    if maven_install_target == None:
        srcs = None
        deps = None
        runtime_deps = [
            "@rules_jooq_flyway_codegen//rules_jooq_flyway_codegen:codegen",
            migration_jar,
        ]
    else:
        srcs = ["@rules_jooq_flyway_codegen//rules_jooq_flyway_codegen:codegen_srcjar"]
        deps = [
            "@" + maven_install_target + "//:mysql_mysql_connector_java",
            "@" + maven_install_target + "//:org_flywaydb_flyway_core",
            "@" + maven_install_target + "//:org_jooq_jooq",
            "@" + maven_install_target + "//:org_jooq_jooq_codegen",
            "@" + maven_install_target + "//:org_jooq_jooq_meta",
            "@" + maven_install_target + "//:org_mariadb_jdbc_mariadb_java_client",
            "@" + maven_install_target + "//:org_postgresql_postgresql",
            "@" + maven_install_target + "//:org_testcontainers_jdbc",
            "@" + maven_install_target + "//:org_testcontainers_mariadb",
            "@" + maven_install_target + "//:org_testcontainers_mysql",
            "@" + maven_install_target + "//:org_testcontainers_postgresql",
            "@" + maven_install_target + "//:org_testcontainers_testcontainers",
            "@" + maven_install_target + "//:org_xerial_sqlite_jdbc",
        ]
        runtime_deps = [migration_jar]
    native.java_binary(
        name = name + "_codegen",
        main_class = "rules_jooq_flyway_codegen.src.dev.richst.jooq_bazel.JooqBazelCodegen",
        srcs = srcs,
        deps = deps,
        visibility = ["//visibility:public"],
        runtime_deps = runtime_deps,
    )
    jooqflyway_gensrcs(
        name = name + "_srcjar",
        migration_jar = migration_jar,
        tool = name + "_codegen",
        codegen_xml = codegen_xml,
        db_type = db_type,
        docker_image = docker_image,
    )

    native.java_library(
        name = name,
        srcs = [":" + name + "_srcjar"],
        visibility = visibility,
        deps = [
            jooq_dep,
            jooq_meta_dep,
        ],
    )
