#!/usr/bin/env python3

import os
import sys
import hashlib
import subprocess

version = sys.argv[1]
print("Releasing version v" + version)
release_file = "rules_jooq_flyway_codegen-v" + version + ".tgz"
subprocess.run(["git", "tag" , "v"+version], check=True)

subprocess.run(["git", "archive", "--format=tar.gz", "-o", release_file, "HEAD"], check=True)
with open(release_file, "rb") as f:
    bytes=f.read()
    checksum = hashlib.sha256(bytes).hexdigest()

lines = []
with open("README.md", "r") as f:
    lines = f.readlines()  

with open("README-new.md", "w") as f:
    for l in lines:
        if "    sha256" in l:
            f.write("        sha256 = \"" + checksum + "\",\n")
        elif "urls = [\"https://github.com/richardstephens/rules_jooq_flyway_codegen" in l:
            f.write("        urls = [\"https://github.com/richardstephens/rules_jooq_flyway_codegen/releases/download"
            +"/v"+version+"/"+release_file+"\"],\n")
        else:
            f.write(l)

os.unlink("README.md")
os.rename("README-new.md", "README.md")


