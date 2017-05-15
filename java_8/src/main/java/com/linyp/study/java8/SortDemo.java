package com.linyp.study.java8;

import com.linyp.study.utils.JsonUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by linyp on 2016/9/27.
 */
public class SortDemo {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(5,1,3,10,6,28,14,23,99,11,45);
        compare(list);
    }

    private static void compare(List<Integer> list) {
        // 匿名内部类
        /*Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });*/
        //Collections.sort(list, (o1, o2) -> o1.compareTo(o2));


        // ================================================  Streams API =========================================
        /*list = list.stream().sorted(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        }).collect(Collectors.toList());*/

        //list = list.stream().sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());

        //list = list.stream().sorted((o1, o2) -> o2.compareTo(o1)).collect(Collectors.toList());

        //list = list.stream().sorted(Integer::compareTo).collect(Collectors.toList());

        //list = list.stream().sorted().collect(Collectors.toList());

        //list = list.stream().sorted(Comparator.comparingInt(Integer::intValue)).collect(Collectors.toList());

        //list = list.stream().sorted(Comparator.comparingInt(Integer::intValue).reversed()).collect(Collectors.toList());


        // 接口中可以定义默认方法，其他方法比如List的forEach
        /*list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });*/

        //list.sort((o1, o2) -> o1.compareTo(o2));

        //list.sort((o1, o2) -> o2.compareTo(o1));

        //list.sort(Integer::compareTo);

        //list.sort(Comparator.comparing(Integer::intValue, (o1, o2) -> o2.compareTo(o1)));

        //list.sort(Comparator.comparing(Integer::intValue).reversed());

        list.sort(Comparator.comparingInt(Integer::intValue).reversed());

        System.out.println(JsonUtils.toJSON(list));
    }
}
