package com.linyp.study.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by linyp on 2016/9/19.
 * 在文档中查找单词的应用程序，所有任务将返回单词在文档的一部分中或行中出现的次数:
 *      ①一个文档任务，将在文档中的行集合中查找一个单词
 *      ②一个行任务，将在文档的一部分数据中查找一个单词
 */
public class Main1 {
    public static void main(String[] args) {
        // 初始化一个100行，每行1000个字符的二维数组
        DocumentMock documentMock = new DocumentMock();
        String[][] document = documentMock.generateDocument(100, 1000, "the");
        // 创建一个统计一组行中单词次数的任务
        DocumentTask task = new DocumentTask(document, 0, 100, "the");
        // 使用无参构造器创建ForkJoinPool对象
        ForkJoinPool pool=new ForkJoinPool();
        // 在池中使用execute()方法执行这个任务，execute()方法异步执行
        pool.execute(task);

        do {
            System.out.printf("******************************************\n");
            System.out.printf("Main: Parallelism: %d\n",pool.getParallelism());
            System.out.printf("Main: Active Threads: %d\n",pool.getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n",pool.getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n",pool.getStealCount());
            System.out.printf("******************************************\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());
        // 关闭池
        pool.shutdown();

        try {
            // 调用awaitTermination()方法等待任务执行结束
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (task.isCompletedNormally()){
            try {
                System.out.println("Main: The word appears  " + task.get() + "  in the document");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 用来模拟文档的字符串二维数组
 */
class DocumentMock {
    // 创建一个带有一些单词的字符串数组。这个数组将被用来生成字符串二维数组
    private String[] words = {"the","hello","goodbye","packt", "java","thread","pool","random","class","main"};

    /**
     * 生成一个字符串二维数组
     * @param numLines  行数
     * @param numWords  每行的单词数
     * @param word
     * @return
     */
    public String[][] generateDocument(int numLines, int numWords, String word) {
        int counter=0;
        String[][] document=new String[numLines][numWords];
        Random random = new Random();
        for (int i = 0; i < numLines; i++){
            for (int j=0; j<numWords; j++) {
                int index = random.nextInt(words.length);
                document[i][j] = words[index];
                if (document[i][j].equals(word)){
                    counter++;
                }
            }
        }
        System.out.println("DocumentMock: The word appears " + counter + " times in the document");
        return document;
    }
}

/**
 * 任务基类，实现DocumentTask和LineTask两个任务类的共用方法
 */
abstract class BaseTask extends RecursiveTask<Integer> {
    /**
     * 计算多个任务返回结果的总和
     * @param numbers
     * @return
     */
    protected Integer groupResults(Integer ... numbers) {
        Integer result = 0;
        for (int i = 0; i < numbers.length; i++) {
            result += numbers[i];
        }
        return result;
    }
}

/**
 * 任务：统计单词在一组行中出现的次数
 */
class DocumentTask extends BaseTask {
    private String[][] document;
    // 起始行、结束行
    private int start, end;
    private String word;

    public DocumentTask(String[][] document, int start, int end, String word) {
        this.document = document;
        this.start = start;
        this.end = end;
        this.word = word;
    }

    @Override
    protected Integer compute() {
        Integer result = 0;
        // 如果属性end和start的差小于10，调用processLines()方法统计单词出现的次数
        if (end - start < 10) {
            result = processLines(document, start, end, word);
        }
        // 分解任务，创建两个DocumentTask对象处理分别处理各自的行组
        else {
            int mid = (start + end) / 2;
            DocumentTask task1 = new DocumentTask(document, start, mid, word);
            DocumentTask task2 = new DocumentTask(document, mid, end, word);
            // 在池中使用invokeAll()方法执行所有任务
            invokeAll(task1, task2);

            try {
                // 获取每个任务的结果并求和
                result = groupResults(task1.get(), task2.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 统计一组行中单词出现的次数
     * @param document  模拟文档的二维字符串数组
     * @param start 起始行下标
     * @param end   结束行下标
     * @param word  需要统计的单词
     * @return
     */
    private Integer processLines(String[][] document,int start, int end, String word) {
        List<LineTask> tasks = new ArrayList<>();
        // 分解任务，每行分别用一个LineTask进行统计
        for (int i = start; i < end; i++) {
            LineTask task = new LineTask(document[i], 0, document[i].length, word);
            tasks.add(task);
        }
        // 调用invokeAll()执行所有任务
        invokeAll(tasks);
        // 计算所有任务返回结果的总和
        Integer result = 0;
        try {
            for (int i = 0; i < tasks.size(); i++) {
                // 获取该任务的结果
                LineTask task = tasks.get(i);
                // 结果累加
                result += task.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
}

/**
 * 任务：统计单词在一行中出现的次数
 */
class LineTask extends BaseTask {
    private static final long serialVersionUID = 1L;
    private String[] line;
    // 行的起始位置、结束位置
    private int start, end;
    private String word;

    public LineTask(String[] line, int start, int end, String word) {
        this.line = line;
        this.start = start;
        this.end = end;
        this.word = word;
    }

    @Override
    protected Integer compute() {
        Integer result = 0;
        // 如果属性end和start之差小于100
        if (end - start < 100) {
            result = count(line, start, end, word);
        }
        // 如果属性end和start之差大于100，则分解成两个LineTask
        else {
            int mid = (start + end) / 2;
            LineTask task1 = new LineTask(line, start, mid, word);
            LineTask task2 = new LineTask(line, mid, end, word);
            // 调用invokeAll()执行所有任务
            invokeAll(task1, task2);

            try {
                // 获取每个任务的结果并求和
                result = groupResults(task1.get(), task2.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 计算行中某一片段中单词出现的次数
     * @param line  当前行
     * @param start 起始位置
     * @param end   结束位置
     * @param word  需要统计的单词
     * @return
     */
    private Integer count(String[] line, int start, int end, String word) {
        int counter = 0;
        for (int i = start; i < end; i++) {
            if (line[i].equals(word))
                counter++;
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return counter;
    }
}
