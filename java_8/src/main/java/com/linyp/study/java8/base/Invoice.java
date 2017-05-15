package com.linyp.study.java8.base;

/**
 * Created by linyp on 2016/8/6.
 */
public class Invoice {
    private Integer id;
    private Double amount;
    private String remark;

    public Invoice(Integer id, Double amount, String remark) {
        this.id = id;
        this.amount = amount;
        this.remark = remark;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
