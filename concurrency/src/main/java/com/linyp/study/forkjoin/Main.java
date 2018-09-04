package com.linyp.study.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * Created by linyp on 2016/9/18.
 */
public class Main {
    public static void main(String[] args) {
        ProductListGenerator generator = new ProductListGenerator();
        // 创建一个包括10000个产品的列表
        List<Product> products = generator.generate(10000);
        // .创建一个新的Task对象，用来更新产品队列中的产品。first参数使用值0，last参数使用值10000
        Task task = new Task(products, 0, products.size(), 0.20);
        // 使用无参构造器创建ForkJoinPool对象
        ForkJoinPool pool = new ForkJoinPool();
        // 在池中使用execute()方法执行这个任务，execute()方法异步执行
        pool.execute(task);

        // 每隔5毫秒池中的变化信息，将池中的一些参数打印出来
        do {
            System.out.println("Main: Thread Count:" + pool.getActiveThreadCount());
            System.out.println("Main: Thread Steal:" + pool.getStealCount());
            System.out.println("Main: Parallelism:" + pool.getParallelism());
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());
        // 关闭池
        pool.shutdown();
        // 使用isCompletedNormally()方法检查任务是否出错
        if (task.isCompletedNormally()) {
            System.out.println("Main: The process has completed normally.");
        }

        // 在增长之后，所有产品的价格应该是12。将价格不是12的所有产品的名称和价格写入到控制台
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getPrice() != 12) {
                System.out.println("Product " + product.getName() + " " + product.getPrice());
            }
        }

        System.out.println("Main: End of the program.");
    }
}

/**
 * Created by linyp on 2016/9/18.
 * 产品
 */
class Product {
    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

/**
 * Created by linyp on 2016/9/18.
 * 产生随机产品列表
 */
class ProductListGenerator {

    /**
     * 接收一个容量参数，返回一个产品列表
     *
     * @param size
     * @return
     */
    public List<Product> generate(int size) {
        List<Product> ret = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Product product = new Product();
            product.setName("Product" + i);
            product.setPrice(10);
            ret.add(product);
        }
        return ret;
    }
}

/**
 * Created by linyp on 2016/9/18.
 * 任务类，继承于RecursiveAction(没有返回值)
 */
class Task extends RecursiveAction {
    private static final long serialVersionUID = 1L;

    private List<Product> products;
    private int first;
    private int last;
    // 存储产品价格的增长
    private double increment;

    // 构造函数，初始化参数
    public Task(List<Product> products, int first, int last, double increment) {
        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }

    @Override
    protected void compute() {
        // 如果last和first的差小于10（任务只能更新价格小于10的产品），使用updatePrices()方法递增的设置产品的价格
        if (last - first < 10) {
            updatePrices();
        }
        // 递归：如果last和first的差大于或等于10，则创建两个新的Task对象，一个处理产品的前半部分，另一个处理产品的后半部分，然后在ForkJoinPool中，使用invokeAll()方法执行它们
        else {
            int middle = (last + first) / 2;
            System.out.println("Task: Pending tasks:" + getQueuedTaskCount());
            Task t1 = new Task(products, first, middle, increment);
            Task t2 = new Task(products, middle + 1, last, increment);
            // 调用invokeAll()方法，执行每个任务所创建的子任务,同步调用，这个任务在继续（可能完成）它的执行之前，必须等待子任务的结束
            invokeAll(t1, t2);
        }
    }

    private void updatePrices() {
        for (int i = first; i < last; i++) {
            Product product = products.get(i);
            product.setPrice(product.getPrice() * (1 + increment));
        }
    }
}

