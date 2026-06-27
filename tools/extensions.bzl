load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def _google_java_format_impl(_ctx):
    http_file(
        name = "google_java_format",
        sha256 = "79e1cb5c8bd698c572c0ae504d07816129b8fc06204fb49550bec83bd5ea0aa8",
        urls = ["https://github.com/google/google-java-format/releases/download/v1.34.1/google-java-format-1.34.1-all-deps.jar"],
    )

google_java_format = module_extension(
    implementation = _google_java_format_impl,
)
