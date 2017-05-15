package com.linyp.study.java8;

import com.linyp.study.java8.base.Invoice;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by linyp on 2016/9/27.
 */
public class StreamDemo1 {
    public static void main(String[] args) {
        Invoice invoice1 = new Invoice(1, 500.0, "NO1Training");
        Invoice invoice2 = new Invoice(2, 900.0, "NO2");
        Invoice invoice3 = new Invoice(3, 1500.0, "NO3Training");
        Invoice invoice4 = new Invoice(4, 2500.0, "NO4");
        Invoice invoice5 = new Invoice(5, 2500.0, "NO5Training");
        List<Invoice> invoices = Arrays.asList(invoice1, invoice2, invoice3, invoice4, invoice5);

        System.out.println(formalWay(invoices));
        System.out.println(streamWay(invoices));
        System.out.println(parallelStreamWay(invoices));
    }

    private static List<Integer> formalWay(List<Invoice> list) {
        List<Invoice> trainingInvoices = new ArrayList<>();
        for(Invoice inv: list) {
            if(inv.getRemark().contains("Training")) {
                trainingInvoices.add(inv);
            }
        }
        Collections.sort(trainingInvoices, new Comparator<Invoice>() {
            @Override
            public int compare(Invoice o1, Invoice o2) {
                return o2.getAmount().compareTo(o1.getAmount());
            }
        });
        List<Integer> invoiceIds = new ArrayList<>();
        for(Invoice inv: trainingInvoices) {
            invoiceIds.add(inv.getId());
        }
        return invoiceIds;
    }

    private static List<Integer> streamWay(List<Invoice> list) {
        List<Integer> invoiceIds = list.stream()
                .filter(invoice -> invoice.getRemark().contains("Training"))
                // reversed表示倒序,不加即默认正序
                .sorted(Comparator.comparingDouble(invoice -> invoice.getAmount()))
                .map(Invoice::getId).collect(Collectors.toList());

        //Integer sumNum = list.stream().mapToInt(Invoice::getId)
        //        .filter(integer -> integer > 1).sum();
        return invoiceIds;
    }

    // 并发执行注意点: http://www.cnblogs.com/gaobig/p/4874400.html
    private static List<Integer> parallelStreamWay(List<Invoice> list) {
        // parallelStream表示并行执行
        List<Integer> invoiceIds = list.parallelStream()
                .filter(invoice -> invoice.getRemark().contains("Training"))
                // reversed表示倒序,不加即默认正序
                .sorted(Comparator.comparingDouble(Invoice::getAmount).reversed())
                .map(Invoice::getId).collect(Collectors.toList());

        /*ForkJoinPool pool = new ForkJoinPool();
        pool.submit(() -> {
            // parallelStream表示并行执行
            list.parallelStream()
                    .filter(invoice -> invoice.getRemark().contains("Training"))
                    // reversed表示倒序,不加即默认正序
                    .sorted(Comparator.comparingDouble(Invoice::getAmount).reversed())
                    .map(Invoice::getId).collect(Collectors.toList());
        });*/

        return invoiceIds;
    }
}

