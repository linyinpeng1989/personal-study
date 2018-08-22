package com.linyp.study.common;

/**
 * @author: Yinpeng.Lin
 * @desc:
 * @date: Created in 2018/8/21 15:42
 */
public class Graph {
    private String key;
    private String value;
    /**
     * 用于确定权重值
     */
    private Integer weight;

    public Graph() {
    }

    public Graph(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Graph(String key, String value, Integer weight) {
        this.key = key;
        this.value = value;
        this.weight = weight;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}
