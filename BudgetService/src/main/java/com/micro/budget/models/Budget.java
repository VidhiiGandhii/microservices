package com.micro.budget.models;

public class Budget {

    private String _budgetId;
    private String _budgetName;
    private double _amount;
    private String _projectId; // Foreign key to link with Project

    public Budget() {
    }

    public Budget(String budgetId, String budgetName, double amount, String projectId) {
        this._budgetId = budgetId;
        this._budgetName = budgetName;
        this._amount = amount;
        this._projectId = projectId;
    }

    public String getBudgetId() {
        return _budgetId;
    }

    public void setBudgetId(String budgetId) {
        this._budgetId = budgetId;
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
