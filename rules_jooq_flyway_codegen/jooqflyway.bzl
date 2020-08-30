def _impl(ctx):
    file = ctx.actions.declare_file(ctx.attr.name + ".srcjar")
    print("To:  " + file.path)
    args = ctx.actions.args()
    args.add(file.path)
    args.add(ctx.attr.db_type)
    args.add_all(ctx.attr.codegen_xml.files)

    ctx.actions.run(
        inputs = ctx.attr.migration_jar.files.to_list() + ctx.attr.codegen_xml.files.to_list(),
        outputs = [file],
        #outputs = [ctx.outputs],
        executable = ctx.executable.tool,
        arguments = [args],
    )

    return [DefaultInfo(files = depset([file]))]

gensrcs = rule(
    implementation = _impl,
    attrs = {
        "migration_jar": attr.label(),
        "codegen_xml": attr.label(allow_single_file = True),
        "tool": attr.label(
            executable = True,
            cfg = "host",
        ),
        "db_type": attr.string(),
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
        **kwargs):
    native.java_binary(
        name = name + "_codegen",
        main_class = "dev.richst.jooq_bazel.JooqBazelCodegen",
        visibility = ["//visibility:public"],
        resource_jars = [
            migration_jar,
        ],
        runtime_deps = [
            "@rules_jooq_flyway_codegen//rules_jooq_flyway_codegen:codegen",
        ],
    )
    gensrcs(
        name = name + "_srcjar",
        migration_jar = migration_jar,
        tool = name + "_codegen",
        codegen_xml = codegen_xml,
        db_type = db_type,
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
