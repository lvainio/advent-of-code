#!/usr/bin/env bash
set -euo pipefail

if [ "$#" -ne 2 ]; then
  echo "Usage: bazel run //tools/generate:generate-java -- <year> <day>" >&2
  exit 1
fi

WS_ROOT="${BUILD_WORKSPACE_DIRECTORY:-$(pwd)}"
echo "LOG: cd to workspace root: $WS_ROOT"
cd "$WS_ROOT"

YEAR="$1"
DAY="$2"
DAY_PADDED=$(printf "%02d" "$DAY")

ROOT_DIR="solutions/${YEAR}/${DAY_PADDED}"
MAIN_DIR="${ROOT_DIR}/src/main/java/me/vainio/aoc/year${YEAR}/day${DAY_PADDED}"
TEST_DIR="${ROOT_DIR}/src/test/java/me/vainio/aoc/year${YEAR}/day${DAY_PADDED}"

if [ -d "$ROOT_DIR" ]; then
  echo "ERROR: Directory already exists: $ROOT_DIR" >&2
  exit 1
fi

echo "LOG: Generating Java solution for Year $YEAR Day $DAY_PADDED"

mkdir -p "$ROOT_DIR" "$MAIN_DIR" "$TEST_DIR"

# Solver.java
echo "LOG: Generating Solver.java"
sed \
  -e "s/{{YEAR}}/$YEAR/g" \
  -e "s/{{DAY}}/$DAY/g" \
  -e "s/{{DAY_PADDED}}/$DAY_PADDED/g" \
  tools/generate/templates/java/Solver.java.template \
  > "${MAIN_DIR}/Solver.java"

# SolverTest.java
echo "LOG: Generating SolverTest.java"
sed \
  -e "s/{{YEAR}}/$YEAR/g" \
  -e "s/{{DAY_PADDED}}/$DAY_PADDED/g" \
  tools/generate/templates/java/SolverTest.java.template \
  > "${TEST_DIR}/SolverTest.java"

# BUILD.bazel
echo "LOG: Generating BUILD.bazel"
sed \
  -e "s/{{YEAR}}/$YEAR/g" \
  -e "s/{{DAY}}/$DAY/g" \
  tools/generate/templates/java/BUILD.bazel.template \
  > "${ROOT_DIR}/BUILD.bazel"

echo "LOG: Generated files in $ROOT_DIR:"
