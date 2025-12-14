#!/usr/bin/env bash

set -euo pipefail

WORKSPACE_ROOT="${BUILD_WORKSPACE_DIRECTORY:-$(pwd)}"

if [ -z "$WORKSPACE_ROOT" ]; then
  echo "Error: Could not find Bazel workspace root" >&2
  exit 1
fi

cd "$WORKSPACE_ROOT"

JAVA_FILES=$(find "$WORKSPACE_ROOT" -name "*.java" -not -path "*/bazel-*" -not -path "*/.git/*" -not -path "*/.bazelbsp/*")

if [ -z "$JAVA_FILES" ]; then
  echo "No Java files found to format"
  exit 0
fi

echo "Formatting Java files..."
echo "$JAVA_FILES" | xargs bazel run //tools/format:google-java-format -- --replace

echo "Done formatting Java files"
