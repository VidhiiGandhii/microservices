package com.micro.budget.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.micro.budget.irepository.IBudgetRepo;
import com.micro.budget.models.Budget;

@Repository
public class BudgetRepo implements IBudgetRepo {

    private JdbcTemplate _JdbcTemplate;

    @Autowired
    public BudgetRepo(JdbcTemplate jdbcTemplate) {
        this._JdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Budget> getAllBudgets() {
        String sql = "SELECT * FROM budget";
        return _JdbcTemplate.query(sql, new BudgetRowMapper());
    }

    @Override
    public Budget getBudgetById(String budgetId) {
        String sql = "SELECT * FROM budget WHERE budget_id = ?";
        return _JdbcTemplate.queryForObject(sql, new BudgetRowMapper(), budgetId);
    }

    @Override
    public Budget getBudgetByName(String budgetName) {
        String sql = "SELECT * FROM budget WHERE budget_name = ?";
        return _JdbcTemplate.queryForObject(sql, new BudgetRowMapper(), budgetName);
    }

    @Override
    public Budget addBudget(Budget budget) {
        String sql = "INSERT INTO budget (budget_id, budget_name, amount, project_id) VALUES (?, ?, ?, ?)";
        _JdbcTemplate.update(sql, budget.getBudgetId(), budget.getBudgetName(), budget.getAmount(), budget.getProjectId());
        return budget;
    }

    @Override
    public Integer getBudgetCount() {
        String sql = "SELECT COUNT(*) FROM budget";
        return _JdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public String getBudgetIdByName(String budgetName) {
        String sql = "SELECT budget_id FROM budget WHERE budget_name = ?";
        return _JdbcTemplate.queryForObject(sql, String.class, budgetName);
    }

    @Override
    public String getBudgetNameById(String budgetId) {
        String sql = "SELECT budget_name FROM budget WHERE budget_id = ?";
        return _JdbcTemplate.queryForObject(sql, String.class, budgetId);
    }

    @Override
    public List<Budget> getBudgetsByProjectId(String projectId) {
        String sql = "SELECT * FROM budget WHERE project_id = ?";
        return _JdbcTemplate.query(sql, new BudgetRowMapper(), projectId);
    }
}
