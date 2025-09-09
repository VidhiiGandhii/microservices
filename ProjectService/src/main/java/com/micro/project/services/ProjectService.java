package  com.micro.project.services;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.project.dto.ProjectDto;
import com.micro.project.irepository.IprojectRepo;
import com.micro.project.models.Project;



@Service(value="ProjectService")
public class ProjectService {

    private final IprojectRepo _IprojectRepo;
    private final ModelMapper _ModelMapper;
    @Autowired
    ProjectService(IprojectRepo projectRepo,ModelMapper modelMapper)
    {
        this._IprojectRepo=projectRepo;
        this._ModelMapper=modelMapper;
    }
public List<ProjectDto> GetAllProjects(){
    List<Project> projects= _IprojectRepo.GetAllProjects();
    List<ProjectDto> projectDtos=new ArrayList<>();
    for(Project p:projects){
        ProjectDto dto=_ModelMapper.map(p, ProjectDto.class);
        projectDtos.add(dto);

    }
    return projectDtos;
}
public ProjectDto GetProjectById(String ProjectId) 

    {
       Project Project=_IprojectRepo.GetProjectById(ProjectId);
        return _ModelMapper.map(Project, ProjectDto.class);
    }

    public String GetProjectTitleById(String ProjectId) 
    {
        return  _IprojectRepo.GetProjectTitleById(ProjectId);
    }

    public String GetProjectIdByTitle(String ProjectTitle) 
    {
        String id = _IprojectRepo.GetProjectIdByTitle(ProjectTitle);
        if (id != null) {
            return id.toLowerCase();
        }
        return null;
    }

    public ProjectDto GetProjectByTitle(String ProjectTitle) 
    {
        Project Project = _IprojectRepo.GetProjectByTitle(ProjectTitle);
        return _ModelMapper.map(Project, ProjectDto.class);
    }

    

    public ProjectDto AddProject(ProjectDto Projectdto) 
    {
        Project Project = _ModelMapper.map(Projectdto, Project.class);
        _IprojectRepo.AddProject(Project);

        return Projectdto;
    }
}
