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
- `algorithm/src/main/java/leetcode/notes` 是软链，指向仓库根的 `docs/` 目录——两处是同一份文件，修改任意一处即改动 git 跟踪的 `docs/` 下的笔记。

## HTML 笔记风格指南

`leetcode_summary/` 下的 HTML 笔记使用以下样式以减少视觉疲劳：

### 色彩基调

- **背景**：柔和的草绿色 `#f2f7ed`（大面积使用，比白色更护眼）
- **正文**：深灰 `#2c3e2d`，不使用纯黑（降低对比度）
- **标题**：深绿 `#2d5a27` / 墨绿 `#3d6b35`

### 代码块

- 使用 **暖灰色** 背景（`#f5f0eb`）替代深色背景，搭配深色文字（`#3d3731`），模拟纸质书效果
- 或者保留深色背景但降低对比度：背景 `#2b2b2b`，文字 `#d4cfc6`

### 高亮框体系（圆角 + 左侧色条）

| 类型 | 色条颜色 | 背景色 | 用途 |
|------|----------|--------|------|
| 核心心法 | 靛蓝 `#6366f1` | `#f0eeff` | 最关键的方法论 |
| 提示 | 琥珀 `#d4a017` | `#fef9e7` | 小技巧 / 注意点 |
| 警告 | 红 `#d9534f` | `#fdf0ef` | 易错点 / 边界情况 |
| 总结 | 翠绿 `#4caf50` | `#edf7ed` | 解题步骤归纳 |

### 降低疲劳的通用手法

1. **充足行高**：`line-height: 1.8` 以上，段落间距 16px+
2. **柔和阴影**：卡片类用 `box-shadow: 0 1px 4px rgba(0,0,0,0.06)`，不刺眼
3. **表格斑马纹**：`tr:nth-child(even)` 用极浅色（`#f4f8f2`），hover 用 `#eaf3e5`
4. **避免荧光色**：高亮只用柔和的背景色块 + 边框，不用 `yellow` 或高饱和色
5. **字体选型**：`-apple-system, "Noto Sans SC", "Microsoft YaHei", sans-serif`，系统字体渲染最平滑
6. **两侧留白**：内容区最大宽度 920px，内边距 32px+
7. **分割线淡化**：`hr` 用 `1px solid #e2e8d8` 而不是纯黑或粗线
8. **分级标题**：h2 用左侧色条 + 稍大字号区分层级，避免加粗过度使用

