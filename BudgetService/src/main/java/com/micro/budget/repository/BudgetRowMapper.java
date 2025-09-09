package com.micro.budget.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.micro.budget.models.Budget;

public class BudgetRowMapper implements RowMapper<Budget> {

    @Override
    public Budget mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs == null) {
            return null;
        }

        String budgetId = rs.getString("budget_id");
        String budgetName = rs.getString("budget_name");
        double amount = rs.getDouble("amount");
        String projectId = rs.getString("project_id");

        return new Budget(budgetId, budgetName, amount, projectId);
    }
}
