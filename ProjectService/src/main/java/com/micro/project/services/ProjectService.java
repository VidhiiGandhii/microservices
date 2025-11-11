package com.micro.project.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.micro.project.dto.ProjectDto;
import com.micro.project.exceptions.InvalidProjectException;
import com.micro.project.exceptions.ProjectAlreadyExistsException;
import com.micro.project.exceptions.ProjectNotFoundException;
import com.micro.project.irepository.IprojectRepo;
import com.micro.project.models.Project;

@Service
public class ProjectService {

    private final IprojectRepo _ProjectRepo;
    private final ModelMapper _modelMapper;

    @Autowired
    public ProjectService(IprojectRepo projectRepo, ModelMapper modelMapper) {
        this._ProjectRepo = projectRepo;
        this._modelMapper = modelMapper;
    }

    public List<ProjectDto> GetAllProjects() {
        List<Project> projects = _ProjectRepo.GetAllProjects();
        // Convert List<Project> to List<ProjectDto>
        return projects.stream()
                .map(project -> _modelMapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }

    public ProjectDto GetProjectById(String projectId) throws ProjectNotFoundException {
        try {
            Project project = _ProjectRepo.GetProjectById(projectId);
            return _modelMapper.map(project, ProjectDto.class);
        } catch (EmptyResultDataAccessException e) {
            // CATCH the DB exception, THROW our custom exception
            throw new ProjectNotFoundException("Project not found with ID: " + projectId);
        }
    }

    public String GetProjectTitleById(String projectId) throws ProjectNotFoundException {
        try {
            return _ProjectRepo.GetProjectTitleById(projectId);
        } catch (EmptyResultDataAccessException e) {
            throw new ProjectNotFoundException("Project not found with ID: " + projectId);
        }
    }

    public String GetProjectIdByTitle(String projectTitle) throws ProjectNotFoundException {
        try {
            return _ProjectRepo.GetProjectIdByTitle(projectTitle);
        } catch (EmptyResultDataAccessException e) {
            throw new ProjectNotFoundException("Project not found with title: " + projectTitle);
        }
    }

    public ProjectDto GetProjectByTitle(String projectTitle) throws ProjectNotFoundException {
        try {
            Project project = _ProjectRepo.GetProjectByTitle(projectTitle);
            return _modelMapper.map(project, ProjectDto.class);
        } catch (EmptyResultDataAccessException e) {
            throw new ProjectNotFoundException("Project not found with title: " + projectTitle);
        }
    }

    public Project AddProject(ProjectDto projectDto) throws InvalidProjectException, ProjectAlreadyExistsException {
        // 1. --- VALIDATION ---
        if (projectDto.getProjecttitle() == null || projectDto.getProjecttitle().trim().isEmpty()) {
            throw new InvalidProjectException("Bad request: Project title cannot be null or empty.");
        }
        if (projectDto.getEpisodes() <= 0) {
            throw new InvalidProjectException("Bad request: Episodes must be greater than 0.");
        }

        // 2. --- "Innovative" Check (Duplicate Title) ---
        try {
            _ProjectRepo.GetProjectIdByTitle(projectDto.getProjecttitle());
            // If the above line *succeeds*, it means the title already exists.
            throw new ProjectAlreadyExistsException("Conflict: A project with title '" + projectDto.getProjecttitle() + "' already exists.");
        } catch (EmptyResultDataAccessException e) {
            // This is the "good" path. The title does not exist.
        }

        // 3. --- Save ---
        Project project = _modelMapper.map(projectDto, Project.class);
        return _ProjectRepo.AddProject(project);
    }
}