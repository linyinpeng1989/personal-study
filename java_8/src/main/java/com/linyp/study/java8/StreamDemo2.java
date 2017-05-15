package com.linyp.study.java8;

import com.linyp.study.java8.base.Invoice;
import com.linyp.study.utils.JsonUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by linyp on 2016/9/27.
 */
public class StreamDemo2 {
    public static void main(String[] args) {
        Invoice invoice1 = new Invoice(1, 500.0, "a");
        Invoice invoice2 = new Invoice(2, 900.0, "b");
        Invoice invoice3 = new Invoice(3, 1500.0, "c");
        Invoice invoice4 = new Invoice(4, 2500.0, "d");
        Invoice invoice5 = new Invoice(5, 2200.0, "d");
        Invoice invoice6 = new Invoice(6, 2300.0, "e");
        Invoice invoice7 = new Invoice(7, 1200.0, "f");

        Invoice invoice8 = invoice7;
        Invoice invoice9 = invoice7;
        List<Invoice> invoices = Arrays.asList(invoice1, invoice2, invoice3, invoice4, invoice5, invoice6, invoice7, invoice8, invoice9);

        // 基础数据类型的相关操作
        List<Integer> result1 = invoices.stream()
                // 过滤
                .filter(invoice -> invoice.getAmount() > 1000.0)
                // 数据转换
                .map(Invoice::getId)
                // 排序：作为比较的对象需要实现Comparable接口
                //.sorted()
                .sorted(Comparator.comparingInt(Integer::intValue).reversed())
                // 去重
                .distinct()
                // 跳过数据
                .skip(1)
                // 限制数量
                .limit(1)
                // 聚合操作
                .collect(Collectors.toList());
        System.out.println(JsonUtils.toJSON(result1));

        Optional<Invoice> optional = invoices.stream().findAny();

        // 不为null
        optional.orElse(new Invoice(1, 0.1, "default"));
        // orElseGet、isPresent、orElseThrow、ifPresent、map、filter等

        // 防止数查询为空导致空指针的情况，为空的时候创建一个空的Optional实例
        Optional<String> optional1 = Optional.ofNullable("sql result");
        optional1.orElse("");
    }
}

