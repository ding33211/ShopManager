package com.soubu.goldensteward.module.server;

/**
 * Created by lakers on 16/11/5.
 */

public class WalletHomeInfoServerParams {

    String all_money;
    String pending_payment;
    String withdrawals_sum;
    String all_income;
    String today_income;
    String week_income;
    String month_income;

    public String getAll_money() {
        return all_money;
    }

    public void setAll_money(String all_money) {
        this.all_money = all_money;
    }

    public String getPending_payment() {
        return pending_payment;
    }

    public void setPending_payment(String pending_payment) {
        this.pending_payment = pending_payment;
    }

    public String getWithdrawals_sum() {
        return withdrawals_sum;
    }

    public void setWithdrawals_sum(String withdrawals_sum) {
        this.withdrawals_sum = withdrawals_sum;
    }

    public String getAll_income() {
        return all_income;
    }

    public void setAll_income(String all_income) {
        this.all_income = all_income;
    }

    public String getToday_income() {
        return today_income;
    }

    public void setToday_income(String today_income) {
        this.today_income = today_income;
    }

    public String getWeek_income() {
        return week_income;
    }

    public void setWeek_income(String week_income) {
        this.week_income = week_income;
    }

    public String getMonth_income() {
        return month_income;
    }

    public void setMonth_income(String month_income) {
        this.month_income = month_income;
    }
}
