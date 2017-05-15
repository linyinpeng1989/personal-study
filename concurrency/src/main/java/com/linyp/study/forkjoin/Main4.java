package com.linyp.study.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by linyp on 2016/9/20.
 * 查找数在数组中的位置,第一个找到这个数的任务将取消剩下的所有任务（未找到这个数的其他任务）
 *
 * 注：Fork/Join框架并没有提供上述功能（框架本身只能取消暂未开始执行的任务），需自己实现（TaskManager）
 */
public class Main4 {
    public static void main(String[] args) {
        ArrayGenerator generator = new ArrayGenerator();
        int[] array = generator.generateArray(1000);
        TaskManager manager = new TaskManager();
        ForkJoinPool pool = new ForkJoinPool();
        SearchNumberTask task = new SearchNumberTask(array, 0, 300, 5, manager);
        pool.execute(task);
        pool.shutdown();

        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main: The program has finished");
    }
}

/**
 * 产生随机的、指定大小的整数数组
 */
class ArrayGenerator {
    public int[] generateArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10);
        }
        return array;
    }
}

/**
 * 管理在ForkJoinPool中执行的所有任务，实现消任务的功能
 */
class TaskManager {
    // 由于任务需要返回值，且值为数组的位置，所以这里参数化为Integer
    private List<ForkJoinTask<Integer>> tasks = new ArrayList<>();

    // 添加ForkJoinTask对象到任务列表
    public void addTask (ForkJoinTask<Integer> task) {
        tasks.add(task);
    }

    // 使用cancel()方法取消在数列中的所有其他ForkJoinTask对象
    public void cancelTasks(ForkJoinTask<Integer> cancelTask) {
        for (ForkJoinTask<Integer> task : tasks) {
            if (task != cancelTask) {
                task.cancel(true);
                ((SearchNumberTask)task).writeCancelMessage();
            }
        }
    }
}

/**
 *
 */
class SearchNumberTask extends RecursiveTask<Integer> {
    // 源数组
    private int[] numbers;
    // 查找范围的起始、结束位置
    private int start, end;
    // 目标数字
    private int number;
    // 持有TaskManager对象，方便回调相关接口取消任务
    private TaskManager manager;
    // 当任务没有找到这个数时，返回-1
    private final static int NOT_FOUND = -1;

    public SearchNumberTask(int[] numbers, int start, int end, int number, TaskManager manager) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
        this.number = number;
        this.manager = manager;
    }

    @Override
    protected Integer compute() {
        //System.out.println("Task: " + start+":"+end);
        int ret;
        // 如果差值大于10，拆分成两个任务
        if (end - start > 100) {
            ret = launchTasks();
        }
        // 如果差值不大于10，查找目标数字
        else {
            ret = lookForNumber();
        }
        return ret;
    }

    private int lookForNumber() {
        for (int i = start; i < end; i++) {
            // 如果找到目标数字
            if (numbers[i] == number) {
                System.out.println("Task: Number " + number + " found in position " + i);
                // 取消其他所有任务
                manager.cancelTasks(this);
                return i;
            }

            // 使任务睡眠1秒
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return NOT_FOUND;
    }

    private int launchTasks() {
        int mid = (start + end) / 2;
        SearchNumberTask task1 = new SearchNumberTask(numbers, start, mid, number, manager);
        SearchNumberTask task2 = new SearchNumberTask(numbers, mid, end, number, manager);
        // 将任务添加到队列中
        manager.addTask(task1);
        manager.addTask(task2);
        // 调用fork()方法异步提交任务到ForkJoinPool
        task1.fork();
        task2.fork();

        // 调用join等待任务执行结束并获取返回值
        int returnValue = task1.join();
        if (returnValue != -1)
            return returnValue;

        returnValue = task2.join();
        return returnValue;
    }

    public void writeCancelMessage() {
        System.out.println("Task: Canceled task from " + start + " to " + end);
    }
}