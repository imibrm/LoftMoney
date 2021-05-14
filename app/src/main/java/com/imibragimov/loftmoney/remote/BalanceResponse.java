package com.imibragimov.loftmoney.remote;

import com.google.gson.annotations.SerializedName;

public class BalanceResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("total_expenses")
    private float totalExpenses;

    @SerializedName("total_income")
    private float totalIncome;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(float totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public float getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(float totalIncome) {
        this.totalIncome = totalIncome;
    }
}
