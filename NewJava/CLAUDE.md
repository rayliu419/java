# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Test Commands

- **Build all modules**: `mvn clean compile`
- **Run all tests**: `mvn test`
- **Run tests in a single module**: `mvn test -pl <module>` (e.g., `-pl algorithm`)
- **Run a single test class**: `mvn test -Dtest=<TestClass>` (e.g., `-Dtest=TwoPointerTest`)
- **Run a single test method**: `mvn test -Dtest=<TestClass>#<methodName>`
- **Package**: `mvn clean package -DskipTests`

## Project Structure

Multi-module Maven project (Java 17) with 7 modules:

- **basic** — Java language fundamentals: Java 8 streams/lambdas, predicates, object mapping (Jackson), string utilities, static order initialization, log processing
- **algorithm** — LeetCode-style algorithm solutions organized by topic: BFS, DFS, DP, binary search, sliding window, two pointers, linked lists, trees, sorting, intervals, prefix sum, stack algorithms, matrix traversal, Kth problems, LFU cache, Twitter design, tree serialization
- **annotation** — Java annotation processing (custom DB table annotations with SQL type mapping) and Lombok usage (builder, superBuilder, data)
- **concurrency** — Java concurrency: thread pools, CompletableFuture, locks, concurrent collections (blocking queues, concurrent maps), countdown latches, semaphores, cyclic barriers
- **compiler** — ANTLR 4 DSL parser example: builds a graph DSL parser from `.g4` grammar files using listeners/visitors
- **codeanalyzer** — Code analysis using Sonar Java frontend (sonar-plugin-api, java-frontend)
- **engineering** — Engineering patterns: rule engine with a breakable rule machine, validators, test utilities

## Key Dependencies

- JUnit 4.12 (test scope)
- Lombok, Jackson, Gson, Guava, Commons Lang3
- ANTLR 4.7.2 runtime
- Sonar plugin API & Java frontend (codeanalyzer)

## Notes

- This repository is used for practicing algorithm problems. Do not directly modify the user's code — provide analysis and suggestions instead.
- When correcting errors, provide detailed reasoning to help the user understand the underlying concepts.
- All modules inherit from the root `pom.xml`
- Test sources use `src/test/java` convention
- ANTLR grammar generates parser code; generated sources are checked in under `compiler/src/main/java/gen/`
- Tests use JUnit 4 (no JUnit 5 / Jupiter)

