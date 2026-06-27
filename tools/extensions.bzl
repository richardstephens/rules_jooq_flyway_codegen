load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def _google_java_format_impl(_ctx):
    http_file(
        name = "google_java_format",
        sha256 = "bfb7f9ead6cd328389bc2da53860443bc0e805dfd08cc889bfdf43b26cb2a6e8",
        urls = ["https://github.com/google/google-java-format/releases/download/v1.35.0/google-java-format-1.35.0-all-deps.jar"],
    )

google_java_format = module_extension(
    implementation = _google_java_format_impl,
)
