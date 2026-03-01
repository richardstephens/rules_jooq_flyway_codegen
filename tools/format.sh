#!/usr/bin/env bash
set -euo pipefail

GJF_JAR="${RUNFILES_DIR:-$0.runfiles}/rules_jooq_flyway_codegen/external/google_java_format/file/downloaded"

cd "$BUILD_WORKSPACE_DIRECTORY"

JAVA_FILES=$(find . -name '*.java' -not -path '*/bazel-*/*' -not -path '*/.git/*')

if [ -z "$JAVA_FILES" ]; then
  echo "No Java files found."
  exit 0
fi

echo "Formatting Java files..."
# shellcheck disable=SC2086
java --add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED \
     --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED \
     --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED \
     --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED \
     --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED \
     -jar "$GJF_JAR" --aosp --replace $JAVA_FILES
echo "Done."
