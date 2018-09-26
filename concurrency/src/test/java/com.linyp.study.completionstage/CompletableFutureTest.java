package com.linyp.study.completionstage;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Yinpeng Lin
 * @date 2018/3/22
 * @desc CompletableFuture使用例子
 */
public class CompletableFutureTest {

    public static void main(String[] args) {
        // completedFutureExample();
        // runAsyncExample();
        // thenApplyExample();
        // thenApplyAsyncExample();
        // thenApplyAsyncWithExecutorExample();
        // thenAcceptExample();
        // thenAcceptAsyncExample();
        // completeExceptionallyExample();
        // cancelExample();
        // applyToEitherExample();
        // acceptEitherExample();
        // runAfterBothExample();
        // thenAcceptBothExample();
        // thenCombineExample();
        // thenCombineAsyncExample();
        // thenComposeExample();
        // anyOfExample();
        // allOfExample();
        allOfAsyncExample();
    }

    /**
     * 创建一个完成的CompletableFuture
     */
    private static void completedFutureExample() {
        //使用一个预定义的结果创建一个完成的CompletableFuture
        CompletableFuture cf = CompletableFuture.completedFuture("message");
        //判断是否执行完成
        assertTrue(cf.isDone());
        //getNow(null)方法在future完成的情况下会返回结果，否则返回null (传入的参数)。
        assertEquals("message", cf.getNow(null));
    }

    /**
     * 运行一个简单的异步阶段
     */
    private static void runAsyncExample() {
        //默认情况下（即指没有传入Executor的情况下），异步执行会使用ForkJoinPool实现，该线程池使用一个后台线程来执行Runnable任务。
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            //判断是否后台线程
            assertTrue(Thread.currentThread().isDaemon());
            twoSecondSleep();
        });
        //判断异步stage是否返回，twoSecondSleep使执行延迟2秒，因此还未返回
        assertFalse(cf.isDone());
        //睡眠3秒，此时异步stage已经返回
        threeSecondSleep();
        //判断异步stage是否返回
        assertTrue(cf.isDone());
    }

    /**
     * 在前一个阶段上应用函数
     */
    private static void thenApplyExample() {
        //then意味着这个阶段的动作发生当前的阶段正常完成之后,Apply意味着返回的阶段将会对结果前一阶段的结果应用一个函数(该函数会阻塞直到执行完成)
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            assertFalse(Thread.currentThread().isDaemon());
            return s.toUpperCase();
        });
        assertEquals("MESSAGE", cf.getNow(null));
    }

    /**
     * 在前一个阶段上异步应用函数
     */
    private static void thenApplyAsyncExample() {
        //使用ForkJoinPool.commonPool()，为守护线程（后台线程）
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().isDaemon());
            twoSecondSleep();
            return s.toUpperCase();
        });
        assertNull(cf.getNow(null));
        //使用join()同步等待cf执行完成并返回结果
        assertEquals("MESSAGE", cf.join());
    }


    //自定义Executor
    private static ExecutorService executorService = Executors.newFixedThreadPool(3, new ThreadFactory() {
        int count = 1;

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "custom-executor-" + count++);
        }
    });

    /**
     * 使用定制的Executor在前一个阶段上异步应用函数
     */
    private static void thenApplyAsyncWithExecutorExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().getName().startsWith("custom-executor-"));
            //自定义Executor并创建线程，不是后台线程
            assertFalse(Thread.currentThread().isDaemon());
            twoSecondSleep();
            return s.toUpperCase();
        }, executorService);
        assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }

    /**
     * 消费前一阶段的结果
     */
    private static void thenAcceptExample() {
        //如果下一阶段接收了当前阶段的结果，但是在计算的时候不需要返回值(它的返回类型是void)， 那么它可以不应用一个函数，而是一个消费者， 调用方法也变成了thenAccept
        StringBuilder sb = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message").thenAccept(s -> sb.append(s));
        assertTrue("sb was empty", sb.length() > 0);
    }

    /**
     * 异步地消费前一阶段的结果
     */
    private static void thenAcceptAsyncExample() {
        StringBuilder sb = new StringBuilder();
        CompletableFuture cf = CompletableFuture.completedFuture("thenAcceptAsync message")
                .thenAcceptAsync(s -> sb.append(s));
        //同步阻塞等待异步执行结果
        cf.join();
        assertTrue("Result was empty", sb.length() > 0);
    }

    /**
     * 完成计算异常
     */
    private static void completeExceptionallyExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
                CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
        CompletableFuture exceptionHandler = cf.handle((s, th) -> (th != null) ? "message upon cancel" : "");
        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        try {
            cf.join();
            fail("Should have thrown an exception");
        } catch(CompletionException ex) { // just for testing
            assertEquals("completed exceptionally", ex.getCause().getMessage());
        }

        assertEquals("message upon cancel", exceptionHandler.join());
    }

    /**
     * 取消计算
     */
    private static void cancelExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
                CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
        CompletableFuture cf2 = cf.exceptionally(throwable -> "canceled message");
        assertTrue("Was not canceled", cf.cancel(true));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        assertEquals("canceled message", cf2.join());
    }

    /**
     * 在两个完成的阶段其中之一上应用函数
     */
    private static void applyToEitherExample() {
        String original = "Message";
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> delayedUpperCase(s))
                //在两个完成的阶段其中之一上应用函数：s -> s + " from applyToEither"
                .applyToEither(
                CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                s -> s + " from applyToEither");

        // 结果可能有两种情况：MESSAGE from applyToEither、message from applyToEither
        String result = cf1.join();
        assertTrue(result.endsWith(" from applyToEither"));
        System.out.println(result);
    }

    /**
     * 在两个完成的阶段其中之一上调用消费函数
     */
    private static void acceptEitherExample(){
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> delayedUpperCase(s))
                // 在两个完成的阶段其中之一上调用消费函数：s -> result.append(s).append("acceptEither")
                .acceptEither(CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                        s -> result.append(s).append("acceptEither"));
        cf.join();
        assertTrue("Result was empty", result.toString().endsWith("acceptEither"));
        System.out.println(result.toString());
    }

    /**
     * 在两个阶段都执行完后运行一个 Runnable
     */
    private static void runAfterBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        //在两个阶段都执行完后运行一个 Runnable:() -> result.append("done")
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).runAfterBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                () -> result.append("done"));
        assertTrue("Result was empty", result.length() > 0);
        System.out.println(result.toString());
    }

    /**
     * 消费两个阶段的结果，使用BiConsumer实现
     */
    private static void thenAcceptBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).thenAcceptBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                (s1, s2) -> result.append(s1 + s2));
        assertEquals("MESSAGEmessage", result.toString());
        System.out.println(result.toString());
    }

    /**
     * 对两个阶段的结果应用函数，使用BiFunction实现
     */
    private static void thenCombineExample() {
        String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original).thenApply(s -> delayedUpperCase(s))
                .thenCombine(CompletableFuture.completedFuture(original).thenApply(s -> delayedLowerCase(s)),
                        (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.getNow(null));
    }

    /**
     * 异步使用BiFunction处理两个阶段的结果
     */
    private static void thenCombineAsyncExample() {
        String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> delayedUpperCase(s))
                .thenCombine(CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                        (s1, s2) -> s1 + s2);
        //调用join方法等待执行完毕并返回结果
        assertEquals("MESSAGEmessage", cf.join());
    }

    /**
     * 组合 CompletableFuture
     */
    private static void thenComposeExample() {
        String original = "Message";
        CompletableFuture cf = CompletableFuture.completedFuture(original).thenApply(s -> delayedUpperCase(s))
                .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(s -> delayedLowerCase(s))
                        .thenApply(s -> upper + s));
        assertEquals("MESSAGEmessage", cf.join());
    }

    /**
     * 当几个阶段中的一个完成，创建一个完成的阶段
     */
    private static void anyOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase2(s)))
                .collect(Collectors.toList());
        CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((res, th) -> {
            if(th == null) {
                String restr = (String) ((CompletableFuture) res).getNow(null);
                assertTrue(StringUtils.isAllUpperCase(restr));
                result.append(res);
            }
        });
        assertTrue("Result was empty", result.length() > 0);
    }

    /**
     * 当所有的阶段都完成后创建一个阶段
     */
    private static void allOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase2(s)))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> assertTrue(StringUtils.isAllUpperCase((String) cf.getNow(null))));
            result.append("done");
        });
    }

    /**
     * 当所有的阶段都完成后异步地创建一个阶段
     */
    private static void allOfAsyncExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> delayedUpperCase2(s)))
                .collect(Collectors.toList());
        CompletableFuture allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> assertTrue(StringUtils.isAllUpperCase((String) cf.getNow(null))));
            result.append("done");
        });
        allOf.join();
        assertTrue("Result was empty", result.length() > 0);
    }





    /**
     * 线程睡眠2秒
     */
    private static void twoSecondSleep() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程睡眠3秒
     */
    private static void threeSecondSleep() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String delayedUpperCase(String s) {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.completedFuture(s).thenApplyAsync(str -> str.toUpperCase(),
                CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
        return stringCompletableFuture.join();
    }

    private static String delayedLowerCase(String s) {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.completedFuture(s).thenApplyAsync(str -> str.toLowerCase(),
                CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
        return stringCompletableFuture.join();
    }

    private static CompletableFuture delayedUpperCase2(String s) {
        return CompletableFuture.completedFuture(s).thenApply(str -> str.toUpperCase());
    }
}
