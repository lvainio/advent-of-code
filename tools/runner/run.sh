#!/bin/bash

# Advent of Code solver runner
# Usage: bazel run //tools/runner:run -- <year> <day> [input_file]

set -euo pipefail

if [ $# -lt 2 ]; then
    echo "Usage: bazel run //tools/runner:run -- <year> <day> [input_file]"
    echo "Examples:"
    echo "  bazel run //tools/runner:run -- 2024 1 < input.txt"
    echo "  bazel run //tools/runner:run -- 2024 1 input.txt"
    echo "  echo 'test input' | bazel run //tools/runner:run -- 2024 1"
    exit 1
fi

YEAR=$1
DAY=$2
INPUT_FILE=${3:-}

# Format day with leading zero for directory structure
DAY_PADDED=$(printf "%02d" $DAY)

echo "🎄 Running AoC $YEAR Day $DAY solver..."

# Construct the Bazel target
TARGET="//years/$YEAR/$DAY_PADDED:solver"

# Check if the BUILD file exists (basic check)
# When running with bazel run, we need to find the workspace root
WORKSPACE_ROOT="."
if [ -f "$BUILD_WORKSPACE_DIRECTORY/MODULE.bazel" ]; then
    WORKSPACE_ROOT="$BUILD_WORKSPACE_DIRECTORY"
elif [ -f "../../../MODULE.bazel" ]; then
    WORKSPACE_ROOT="../../.."
elif [ -f "../../MODULE.bazel" ]; then
    WORKSPACE_ROOT="../.."
elif [ -f "../MODULE.bazel" ]; then
    WORKSPACE_ROOT=".."
fi

BUILD_FILE="$WORKSPACE_ROOT/years/$YEAR/$DAY_PADDED/BUILD.bazel"
if [ ! -f "$BUILD_FILE" ]; then
    echo "❌ Error: Day $DAY for year $YEAR not found!"
    echo "Expected BUILD file at: $BUILD_FILE"
    echo "Working directory: $(pwd)"
    echo "Workspace root: $WORKSPACE_ROOT"
    echo "Make sure the day directory and BUILD.bazel file exist."
    exit 1
fi

echo "🧩 Solving with target: $TARGET"

# Build the target to ensure it exists
echo "🔨 Building solver..."
cd "$WORKSPACE_ROOT"
if ! bazel build "$TARGET"; then
    echo "❌ Error: Failed to build target $TARGET"
    echo "Make sure the solver code compiles correctly."
    exit 1
fi

# Find the built binary
BINARY_PATH=$(bazel info bazel-bin)/years/$YEAR/$DAY_PADDED/solver
if [ ! -f "$BINARY_PATH" ]; then
    echo "❌ Error: Built binary not found at: $BINARY_PATH"
    echo "The build may have failed or the binary name might be different."
    exit 1
fi

echo "📍 Using solver at: $BINARY_PATH"

# Handle input: from file, stdin, or show usage
if [ -n "$INPUT_FILE" ]; then
    # Input from file
    if [ ! -f "$INPUT_FILE" ]; then
        echo "❌ Error: Input file '$INPUT_FILE' not found!"
        exit 1
    fi
    echo "📥 Reading input from: $INPUT_FILE"
    RESULTS=$("$BINARY_PATH" < "$INPUT_FILE")
elif [ ! -t 0 ]; then
    # Input from stdin (pipe)
    echo "📥 Reading input from stdin..."
    RESULTS=$("$BINARY_PATH")
else
    # No input provided
    echo "❌ Error: No input provided!"
    echo "Either pipe input or provide a file:"
    echo "  echo 'input data' | bazel run //tools/runner:run -- $YEAR $DAY"
    echo "  bazel run //tools/runner:run -- $YEAR $DAY input.txt"
    exit 1
fi

# Parse and display results
PART1=$(echo "$RESULTS" | sed -n '1p')
PART2=$(echo "$RESULTS" | sed -n '2p')

echo "✨ Results:"
echo "  Part 1: $PART1"
echo "  Part 2: $PART2"

echo "🎉 Done!"

# TODO: When aoc_client is ready, add:
# echo "📤 Submitting results..."
# echo -e "$PART1\n$PART2" | bazel run //aoc_client:submit -- --year $YEAR --day $DAY
