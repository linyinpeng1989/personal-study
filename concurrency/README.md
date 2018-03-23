## 多线程并发相关知识点介绍
#### forkjoin目录

ForkJoin并发框架相关练习

#### completionstage目录
Java 8 中 CompletionStage API和它的标准库的实现 CompletableFuture。

CompletableFuture是CompletionInterface接口的实现，它代表某个同步或异步计算的一个阶段。你可以把它理解为是一个为了产生有价值最终结果的计算的流水线上的一个单元。
这意味着多个ComletionStage指令可以链接起来从而一个阶段的完成可以触发下一个阶段的执行。