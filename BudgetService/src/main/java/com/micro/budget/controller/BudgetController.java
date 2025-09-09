package com.micro.budget.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.budget.dto.BudgetDto;
import com.micro.budget.services.BudgetService;

@RequestMapping("/budgets")
@RestController
public class BudgetController {

    private final BudgetService _BudgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this._BudgetService = budgetService;
    }

    /*
     * http://localhost:4004/budgets/allbudgets
     */
    @GetMapping(path = "/allbudgets")
    public List<BudgetDto> getAllBudgets() {
        return _BudgetService.getAllBudgets();
    }

    // http://localhost:4004/budgets/{budgetId}
    @GetMapping(path = "/{budget_id}")
    public BudgetDto getBudgetById(@PathVariable("budget_id") String budgetId) {
        return _BudgetService.getBudgetById(budgetId);
    }

    // http://localhost:4004/budgets/name/{budgetName}
    @GetMapping(path = "/name/{budget_name}")
    public BudgetDto getBudgetByName(@PathVariable("budget_name") String budgetName) {
        return _BudgetService.getBudgetByName(budgetName);
    }

    // http://localhost:4004/budgets/count
    @GetMapping(path = "/count")
    public Integer getBudgetCount() {
        return _BudgetService.getBudgetCount();
    }

    // http://localhost:4004/budgets/idByName/{budgetName}
    @GetMapping(path = "/idByName/{budget_name}")
    public String getBudgetIdByName(@PathVariable("budget_name") String budgetName) {
        return _BudgetService.getBudgetIdByName(budgetName);
    }

    // http://localhost:4004/budgets/nameById/{budgetId}
    @GetMapping(path = "/nameById/{budget_id}")
    public String getBudgetNameById(@PathVariable("budget_id") String budgetId) {
        return _BudgetService.getBudgetNameById(budgetId);
    }

    // http://localhost:4004/budgets/project/{projectId}
    @GetMapping(path = "/project/{project_id}")
    public List<BudgetDto> getBudgetsByProjectId(@PathVariable("project_id") String projectId) {
        return _BudgetService.getBudgetsByProjectId(projectId);
    }

    // Add new Budget
    @PostMapping
    public BudgetDto addBudget(@RequestBody BudgetDto budgetDto) {
        _BudgetService.addBudget(budgetDto);
        return budgetDto;
    }
}
