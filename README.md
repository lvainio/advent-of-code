# Advent Of Code ⭐⭐

## Prerequisites

The only required tool is [Bazelisk](https://github.com/bazelbuild/bazelisk).

## Usage

### Get the Advent Of Code access token

To fetch your personal input data, you need to provide your session token from Advent Of Code. 
Log in to [Advent Of Code](https://adventofcode.com/) and inspect your browser cookies to find
the `session` cookie value. Copy this value and export it as an environment variable:

```bash
export AOC_SESSION="your_session_token_here"
```

### Generate boilerplate code for a day

Java:
```bash
bazel run //tools/generate:generate -- <year> <day>
```

### Fetch input data for a day

```bash
bazel run //tools/aoc-client:aoc_client -- <year> <day>
```

### Run solver for a day

```bash
bazel run //solutions/<year>/day<day>:solver
```

### Post answers to Advent Of Code

```bash
bazel run //tools/aoc-client:aoc_client -- <year> <day> --part1 --part2
```

### Format code

```bash
./tools/format/format.sh
```

### Repin dependencies

If you change any Maven artifacts, update the Maven lockfile with:
```bash
REPIN=1 bazel run @maven//:pin
```

If you change any Bazel dependencies, update the Bazel lockfile with:
```bash
REPIN=1 bazel mod tidy
```

## Solutions

| Year | Language | Link |
|------|----------|-------|
| 2025 | Java     | [View](https://github.com/lvainio/advent-of-code/tree/main/solutions/2025) |
| 2024 | Java     | [View](https://github.com/lvainio/advent-of-code/tree/main/solutions/2024) |
| 2023 |          | [View](https://github.com/lvainio/advent-of-code/tree/main/solutions/2023) |
| 2022 |          | [View](https://github.com/lvainio/advent-of-code/tree/main/solutions/2022) |
| 2021 |          | [View](https://github.com/lvainio/advent-of-code/tree/main/solutions/2021) |
| 2020 |          | [View](https://github.com/lvainio/advent-of-code/tree/main/solutions/2020) |
| 2019 |          | [View](https://github.com/lvainio/advent-of-code/tree/main/solutions/2019) |
| 2018 |          | [View](https://github.com/lvainio/advent-of-code/tree/main/solutions/2018) |
| 2017 |          | [View](https://github.com/lvainio/advent-of-code/tree/main/solutions/2017) |
| 2016 |          | [View](https://github.com/lvainio/advent-of-code/tree/main/solutions/2016) |
| 2015 |          | [View](https://github.com/lvainio/advent-of-code/tree/main/solutions/2015) |
