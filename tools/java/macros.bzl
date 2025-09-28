"""Macros for Java Advent of Code solutions."""

load("@contrib_rules_jvm//java:defs.bzl", "java_test_suite")
load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("//tools/java:deps.bzl", "JUNIT5_DEPS")

def java_day(year, day):
    """Creates the standard Java Advent of Code day setup.
    
    Args:
        year: The year as an integer (e.g. 2024)
        day: The day as an integer (e.g. 1 for day 1)
    """
    # Format day with leading zero for the main class
    year_str = str(year)
    day_str = str(day)
    if day < 10:
        day_padded = "0" + day_str
    else:
        day_padded = day_str
    main_class = "me.vainio.year{}.day{}.Solver".format(year_str, day_padded)
    
    java_library(
        name = "solver_library",
        srcs = native.glob(["src/main/java/**/*.java"]),
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
        runner = "junit5",
        deps = [
            ":solver_library",
        ] + JUNIT5_DEPS,
    )
