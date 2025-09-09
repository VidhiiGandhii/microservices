package com.micro.budget.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BudgetDto {

    @JsonProperty("budgetName")
    private String _budgetName;
    @JsonProperty("amount")
    private double _amount;
    @JsonProperty("projectId")
    private String _projectId; // To link budget with a project

    public BudgetDto() {
    }

    public BudgetDto(String budgetName, double amount, String projectId) {
        this._budgetName = budgetName;
        this._amount = amount;
        this._projectId = projectId;
    }

    public String getBudgetName() {
        return _budgetName;
    }

    public void setBudgetName(String budgetName) {
        this._budgetName = budgetName;
    }

    public double getAmount() {
        return _amount;
    }

    public void setAmount(double amount) {
        this._amount = amount;
    }

    public String getProjectId() {
        return _projectId;
    }

    public void setProjectId(String projectId) {
        this._projectId = projectId;
    }
}
