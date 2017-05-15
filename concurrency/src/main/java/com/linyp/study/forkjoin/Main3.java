package com.linyp.study.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by linyp on 2016/9/20.
 */
public class Main3 {
    public static void main(String[] args) {
        int[] array = new int[100];
        Task3 task = new Task3(array, 0, 100);
        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(task);

        pool.shutdown();

        try {
            // 使用awaitTermination()方法等待任务的结束
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 使用isCompletedAbnormally()方法，检查这个任务或它的子任务是否已经抛出异常
        if (task.isCompletedAbnormally()) {
            System.out.printf("Main: An exception has ocurred\n");
            // 调用getException()获取已经抛出的异常
            System.out.printf("Main: %s\n",task.getException());
        }
        System.out.printf("Main: Result: %d",task.join());
    }
}

class Task3 extends RecursiveTask<Integer> {
    private int[] array;
    private int start, end;

    public Task3(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        System.out.println("Task3: Start from " + start + " to " + end);
        if (end - start < 100) {
            if (start < 3 && end > 3) {
                // 抛出已检查异常，需要显式地捕获处理，否则编译不通过
                //throw new IOException("IO Exception");
                // 抛出运行时异常
                throw new RuntimeException("This task throws an Exception: Task from " + start + " to " + end);
                /*
                // 声明异常，包装并抛出
                Exception e = new Exception("This task throws an Exception: "+ "Task from "+start+" to "+end);
                completeExceptionally(e);
                */
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            int mid = (start + end) / 2;
            Task3 task1 = new Task3(array, start, mid);
            Task3 task2 = new Task3(array, mid, end);
            invokeAll(task1, task2);
        }
        System.out.println("Task3: End from " + start + " to " + end);
        return 0;
    }
}
