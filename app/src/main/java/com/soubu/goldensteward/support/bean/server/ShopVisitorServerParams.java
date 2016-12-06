package com.soubu.goldensteward.support.bean.server;

/**
 * Created by dingsigang on 16-11-9.
 */
public class ShopVisitorServerParams {
    String visit_count;
    String date;
    String refund;

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getVisit_count() {
        return visit_count;
    }

    public void setVisit_count(String visit_count) {
        this.visit_count = visit_count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
