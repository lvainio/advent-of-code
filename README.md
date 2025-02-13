# Advent Of Code ⭐⭐

This repository contains my solutions to Advent Of Code.

## Prerequisites

Before running this project, make sure you have the following software installed:

1. **Java 23** (or higher)  
2. **Maven**

## Usage

The program automatically retrieves the problem input, and therefore requires the Advent of Code session cookie. To use it, retrieve the session cookie from your browser and set it as an environment variable `AOC_SESSION`. For example, in Windows it can be set with the following command:

```bash
setx AOC_SESSION "<your_session_cookie_here>"
```

Build the project with the following command:

```bash
mvn clean install
```

Run the program with following Maven command with the `-year` and `-day` flags to specify the year and day of the puzzle:

```bash
mvn exec:java -Dyear=<year> -Dday=<day>
```

To automate posting answers to the Advent of Code website, you can use the following commands. Choose the appropriate command based on which part of the challenge you want to submit:

```bash
mvn exec:java -Dyear=<year> -Dday=<day> "-Dexec.args=-p1 -p2"
mvn exec:java -Dyear=<year> -Dday=<day> "-Dexec.args=-p1"
mvn exec:java -Dyear=<year> -Dday=<day> "-Dexec.args=-p2"
```

## Solutions

### 2024

Java

### 2023

Python

### 2022

Python

### 2021

Typescript

### 2020

Python, Java, Kotlin, C, Prolog, Go, Javascript, Typescript, C++, Haskell, Ruby, Rust, PHP, Perl, Scala

### 2019

Rust

### 2018

### 2017

### 2016

### 2015

Java
