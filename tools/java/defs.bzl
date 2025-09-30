"""Defs for Java Advent of Code solutions."""

load("@contrib_rules_jvm//java:defs.bzl", "java_test_suite")
load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("//tools/java:deps.bzl", "JUNIT5_DEPS")

def java_day(year, day):
    """Creates a standard Java Advent of Code day setup.
    
    Args:
        year: The year as an integer (e.g. 2024)
        day: The day as an integer (e.g. 1)
    """
    year_str = str(year)
    day_str = str(day)
    if day < 10:
        day_padded = "0" + day_str
    else:
        day_padded = day_str
    main_class = "me.vainio.aoc.year{}.day{}.Solver".format(year_str, day_padded)
    
    java_library(
        name = "solver_library",
        srcs = native.glob(["src/main/java/**/*.java"]),
        resources = native.glob(["src/main/resources/**/*"], allow_empty = True),
        deps = [
            "//libraries/java:commons",
        ],
    )
    
    java_binary(
        name = "solver",
        main_class = main_class,
        runtime_deps = [":solver_library"],
        visibility = ["//visibility:public"],
    )
    
    java_test_suite(
        name = "all_tests",
        srcs = native.glob(["src/test/java/**/*Test.java"]),
        resources = native.glob([
            "src/test/resources/**/*",
            "src/main/resources/**/*"
        ], allow_empty = True),
        runner = "junit5",
        deps = [
            ":solver_library",
        ] + JUNIT5_DEPS,
    )
