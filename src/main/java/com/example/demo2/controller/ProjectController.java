package com.example.demo2.controller;

import com.example.demo2.model.Project;
import com.example.demo2.service.ProjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/commonCodes")
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "jwtAuth")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/getProjectsWithModules")
    public ResponseEntity<List<Map<String,Object>>> getProjectsWithModules(){
        return ResponseEntity.ok(projectService.getProjectsWithModules());
    }

    @PostMapping("/addProjects")
    public ResponseEntity<Project> addProject(@RequestBody Project p){
        return ResponseEntity.ok(projectService.addProject(p));
    }
}
