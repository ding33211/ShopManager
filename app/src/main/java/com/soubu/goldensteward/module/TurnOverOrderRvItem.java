package com.soubu.goldensteward.module;

import java.util.Date;

/**
 * Created by lakers on 16/10/25.
 */
public class TurnOverOrderRvItem {
    int type;
    Date date;
    String imageUrl;
    String company;
    String phone;
    String address;
    String forWhat;
    String unit;
    String amount;
    String price;
    int postageMode;
    int refundMode;
    String customerService;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getForWhat() {
        return forWhat;
    }

    public void setForWhat(String forWhat) {
        this.forWhat = forWhat;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPostageMode() {
        return postageMode;
    }

    public void setPostageMode(int postageMode) {
        this.postageMode = postageMode;
    }

    public int getRefundMode() {
        return refundMode;
    }

    public void setRefundMode(int refundMode) {
        this.refundMode = refundMode;
    }

    public String getCustomerService() {
        return customerService;
    }

    public void setCustomerService(String customerService) {
        this.customerService = customerService;
    }
}
