package com.linyp.study.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by linyp on 2016/9/20.
 * 在一个文件夹及其子文件夹内查找指定扩展名的文件
 */
public class Main2 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FolderTask system = new FolderTask("C:\\Windows", "log");
        FolderTask apps = new FolderTask("C:\\Program Files","log");
        FolderTask documents=new FolderTask("C:\\Documents And Settings","log");

        // 在池中使用execute()方法分别执行3个任务，execute()方法异步执行
        pool.execute(system);
        pool.execute(apps);
        pool.execute(documents);

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
        } while((!system.isDone())||(!apps.isDone())||(!documents.isDone()));
        // 关闭池
        pool.shutdown();

        List<String> results;
        results = system.join();
        System.out.println("System: " + results.size() + " files found.");
        results = apps.join();
        System.out.println("Apps: " + results.size() + " files found.");
        results = documents.join();
        System.out.println("Documents: " + results.size() + " files found.");
    }
}


/**
 * 处理文件夹的内容的任务：
 *      ①对于文件夹里的每个子文件夹，它将以异步的方式提交一个新的任务给ForkJoinPool类
 *      ②对于文件夹里的每个文件，任务将检查文件的扩展名，如果它被处理，并把它添加到结果列表
 */
class FolderTask extends RecursiveTask<List<String>> {
    private static final long serialVersionUID = 1L;
    // 需要处理的文件夹全路径
    private String path;
    // 需要查找的扩展名
    private String extension;

    public FolderTask(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    @Override
   protected List<String> compute() {
        // 存放查找结果
        List<String> list = new ArrayList<>();
        // 保存处理当前文件夹下子文件夹的子任务列表
        List<FolderTask> subTasks = new ArrayList<>();

        File file = new File(path);
        // 获取该文件夹下的文件列表
        File[] content = file.listFiles();
        if (content != null) {
            // 遍历文件列表
            for (int i = 0; i < content.length; i++) {
                // 如果文件是目录
                if (content[i].isDirectory()) {
                    // 创建一个子任务
                    FolderTask task = new FolderTask(content[i].getAbsolutePath(), extension);
                    // 调用fork()方法将任务提交给ForkJoinPool（异步），因此外层task不会阻塞，可以继续往下执行
                    task.fork();
                    // 添加到子任务列表中
                    subTasks.add(task);
                }
                // 如果文件不是目录，校验扩展名，若通过则添加到结果列表
                else {
                    if (content[i].getName().endsWith(extension))
                        list.add(content[i].getAbsolutePath());
                }
            }
            // 如果子任务列表大小超过50，打印日志
            if (subTasks.size() > 50)
                System.out.println(file.getAbsoluteFile() + ": " + subTasks.size() + " tasks ran.");
        }
        // 将子任务的结果添加到结果列表中
        addResultsFromSubTasks(list, subTasks);
        return list;
    }

    private void addResultsFromSubTasks(List<String> list, List<FolderTask> subTasks) {
        // 遍历子任务列表，调用join()方法来等待任务的结束，并获得它们的结果，然后将结果添加到结果列表
        subTasks.forEach(subTask -> list.addAll(subTask.join()));
    }
}
