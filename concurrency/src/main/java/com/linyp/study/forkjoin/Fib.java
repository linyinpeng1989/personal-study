package com.linyp.study.forkjoin;

import EDU.oswego.cs.dl.util.concurrent.FJTask;
import EDU.oswego.cs.dl.util.concurrent.FJTaskRunnerGroup;

/**
 * Created by linyp on 2016/9/18.
 *
 * 斐波那契数列
 * F（n）=F(n-1)+F(n-2)
 * 0,1,1,2,3,5,8,13....
 */
class Fib extends FJTask {
    static final int threshold = 13;
    volatile int number; // arg/result

    Fib(int n) {
        number = n;
    }

    int getAnswer() {
        if (!isDone())
            throw new IllegalStateException();
        return number;
    }

    public void run() {
        int n = number;
        if (n <= threshold) // granularity ctl
            number = seqFib(n);
        else {
            Fib f1 = new Fib(n-1);
            Fib f2 = new Fib(n-2);
            // 调用coInvoke来分解合并两个或两个以上的任务
            coInvoke(f1, f2);
            number = f1.number + f2.number;
        }
    }

    public static void main(String[] args) {
        try {
            int groupSize = 2; // for example
            // FJTaskRunnerGroup（控制及管理）用来启动工作线程池，并初始化执行一个由正常的线程调用所触发的fork/join任务
            FJTaskRunnerGroup group = new FJTaskRunnerGroup(groupSize);
            Fib f = new Fib(35);
            group.invoke(f);
            int result = f.getAnswer();
            System.out.println("Answer: " + result);
        } catch (InterruptedException ex) {
        }
    }

    int seqFib(int n) {
        if (n <= 1) return n;
        else return seqFib(n-1) + seqFib(n-2);
    }
}
