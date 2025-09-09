package com.micro.budget.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.micro.budget.dto.BudgetDto;
import com.micro.budget.dto.ProjectDto;
import com.micro.budget.irepository.IBudgetRepo;
import com.micro.budget.models.Budget;



@Service(value = "BudgetService")
public class BudgetService {

    private final IBudgetRepo _IBudgetRepo;
    private final ModelMapper _ModelMapper;
    private final RestClient _ProjectRestClient;

    @Autowired
    public BudgetService(IBudgetRepo budgetRepo, ModelMapper modelMapper, RestClient.Builder restClientBuilder) {
        this._IBudgetRepo = budgetRepo;
        this._ModelMapper = modelMapper;
        this._ProjectRestClient = restClientBuilder.baseUrl("http://localhost:4002/projects").build();
    }

    public List<BudgetDto> getAllBudgets() {
        List<Budget> budgets = _IBudgetRepo.getAllBudgets();
        List<BudgetDto> budgetDtos = new ArrayList<>();
        for (Budget b : budgets) {
            // Optionally fetch project details if needed, similar to CastingService
            // GetProjectByBudgetId(b.getBudgetId());
            budgetDtos.add(_ModelMapper.map(b, BudgetDto.class));
        }
        return budgetDtos;
    }

    public BudgetDto getBudgetById(String budgetId) {
        Budget budget = _IBudgetRepo.getBudgetById(budgetId);
        return _ModelMapper.map(budget, BudgetDto.class);
    }

    public BudgetDto getBudgetByName(String budgetName) {
        Budget budget = _IBudgetRepo.getBudgetByName(budgetName);
        return _ModelMapper.map(budget, BudgetDto.class);
    }

    public BudgetDto addBudget(BudgetDto budgetDto) {
        Budget budget = _ModelMapper.map(budgetDto, Budget.class);
        // Generate a new budget ID if not provided
        if (budget.getBudgetId() == null || budget.getBudgetId().isEmpty()) {
            String newBudgetId = "B" + (_IBudgetRepo.getBudgetCount() + 100 + 1);
            budget.setBudgetId(newBudgetId);
        }
        _IBudgetRepo.addBudget(budget);
        return budgetDto;
    }

    public Integer getBudgetCount() {
        return _IBudgetRepo.getBudgetCount();
    }

    public String getBudgetIdByName(String budgetName) {
        return _IBudgetRepo.getBudgetIdByName(budgetName);
    }

    public String getBudgetNameById(String budgetId) {
        return _IBudgetRepo.getBudgetNameById(budgetId);
    }

    public List<BudgetDto> getBudgetsByProjectId(String projectId) {
        List<Budget> budgets = _IBudgetRepo.getBudgetsByProjectId(projectId);
        List<BudgetDto> budgetDtos = new ArrayList<>();
        for (Budget b : budgets) {
            budgetDtos.add(_ModelMapper.map(b, BudgetDto.class));
        }
        return budgetDtos;
    }

    // Example of calling Project Service (if needed)
    // You might need to add an endpoint in ProjectService to get projects by budgetId
    
    public List<ProjectDto> getProjectsByBudgetId(String budgetId) {
        return _ProjectRestClient.get().uri("/project/budgetId/{budget_id}", budgetId)
                .retrieve().body(new ParameterizedTypeReference<List<ProjectDto>>() {});
    }
    
}
