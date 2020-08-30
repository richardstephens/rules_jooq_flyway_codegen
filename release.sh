#!/bin/bash

VERSION=$1
git archive --format=tar.gz -o rules_jooq_flyway_codegen-v$VERSION.tgz master
SHA=$(shasum -a 256 rules_jooq_flyway_codegen-v$VERSION.tgz)
SHA=${SHA:0:64}
echo "sha256 = \"$SHA\""
