package com.micro.budget.irepository;

import java.util.List;

import com.micro.budget.models.Budget;

public interface IBudgetRepo {

    List<Budget> getAllBudgets();
    Budget getBudgetById(String budgetId);
    Budget getBudgetByName(String budgetName);
    Budget addBudget(Budget budget);
    Integer getBudgetCount();
    String getBudgetIdByName(String budgetName);
    String getBudgetNameById(String budgetId);
    List<Budget> getBudgetsByProjectId(String projectId);
}

