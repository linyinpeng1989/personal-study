package com.linyp.study.java8.base;

/**
 * Created by linyp on 2016/8/6.
 */
public class SubInvoice implements Comparable<SubInvoice> {
    private Integer id;
    private Double amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(SubInvoice subInvoice) {
        return 0;
    }
}
