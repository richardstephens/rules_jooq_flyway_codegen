"""Rules for generating jOOQ classes from Flyway migrations."""

load("@rules_java//java:defs.bzl", "java_binary", "java_library")

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
            cfg = "exec",
        ),
        "db_type": attr.string(),
        "docker_image": attr.string(),
    },
)

def jooqflyway(
        name,
        migration_jar,
        visibility,
        codegen_xml,
        db_type,
        generator_deps,
        library_deps = [
            "@maven//:org_jooq_jooq",
            "@maven//:org_jooq_jooq_meta",
        ],
        docker_image = "--",
        **kwargs):
    runtime_deps = [
        "@rules_jooq_flyway_codegen//rules_jooq_flyway_codegen:codegen",
        migration_jar,
    ] + generator_deps

    java_binary(
        name = name + "_codegen",
        main_class = "rules_jooq_flyway_codegen.src.dev.richst.jooq_bazel.JooqBazelCodegen",
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

    java_library(
        name = name,
        srcs = [":" + name + "_srcjar"],
        visibility = visibility,
        deps = library_deps,
    )
