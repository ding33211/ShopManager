package com.soubu.goldensteward.support.bean.server;

/**
 * Created by dingsigang on 16-11-8.
 */
public class IncomeOrExpensesServerParams {
    String price;
    String real_get;
    String order_num;
    String order_type;
    String type;
    String status;
    String add_time;
    String message;

    public String getReal_get() {
        return real_get;
    }

    public void setReal_get(String real_get) {
        this.real_get = real_get;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
