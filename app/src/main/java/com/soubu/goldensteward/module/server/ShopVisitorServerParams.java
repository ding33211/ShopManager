package com.soubu.goldensteward.module.server;

/**
 * Created by dingsigang on 16-11-9.
 */
public class ShopVisitorServerParams {
    String visit_count;
    String date;
    String return_rates;

    public String getReturn_rates() {
        return return_rates;
    }

    public void setReturn_rates(String return_rates) {
        this.return_rates = return_rates;
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
