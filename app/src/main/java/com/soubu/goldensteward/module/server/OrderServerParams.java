package com.soubu.goldensteward.module.server;

/**
 * Created by dingsigang on 16-11-9.
 */
public class OrderServerParams {
    String status;
    String sed_status;
    String time;
    String p_count;
    String sum_price;
    String freight;

    ProductInOrderListServerParams[] detail;

    public ProductInOrderListServerParams[] getDetail() {
        return detail;
    }

    public void setDetail(ProductInOrderListServerParams[] detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSed_status() {
        return sed_status;
    }

    public void setSed_status(String sed_status) {
        this.sed_status = sed_status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getP_count() {
        return p_count;
    }

    public void setP_count(String p_count) {
        this.p_count = p_count;
    }

    public String getSum_price() {
        return sum_price;
    }

    public void setSum_price(String sum_price) {
        this.sum_price = sum_price;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }
}
