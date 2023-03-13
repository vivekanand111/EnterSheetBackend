package com.example.demo2.service;

import com.example.demo2.model.Project;
import com.example.demo2.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Map<String, Object>> getProjectsWithModules() {
        List<Project> projectList = projectRepository.findAll();
        List<Map<String, Object>> list =
                projectList.stream().map(project -> {
                    Map<String, Object> p = new HashMap();
                    p.put("projectName",project.getProject_name());
                    List<String> modules =
                            project.getModules().stream()
                                    .map(module -> module.getModule_name())
                                    .collect(Collectors.toList());
                    p.put("modules", modules);
                    return p;
                }).collect(Collectors.toList());
        return list;
    }

    public Project addProject(Project p) {
         return projectRepository.save(p);
    }

}

