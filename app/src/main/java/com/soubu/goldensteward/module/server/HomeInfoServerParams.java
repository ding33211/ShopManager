package com.soubu.goldensteward.module.server;

/**
 * Created by lakers on 16/11/5.
 */

public class HomeInfoServerParams {
    String money;
    String visit;
    String product_visit;
    String order_count;
    String refunds;
    String my_wallet;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getProduct_visit() {
        return product_visit;
    }

    public void setProduct_visit(String product_visit) {
        this.product_visit = product_visit;
    }

    public String getOrder_count() {
        return order_count;
    }

    public void setOrder_count(String order_count) {
        this.order_count = order_count;
    }

    public String getRefunds() {
        return refunds;
    }

    public void setRefunds(String refunds) {
        this.refunds = refunds;
    }

    public String getMy_wallet() {
        return my_wallet;
    }

    public void setMy_wallet(String my_wallet) {
        this.my_wallet = my_wallet;
    }
}
