package com.example.demo2.controller;

import com.example.demo2.model.Task;
import com.example.demo2.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/task")
@Log4j2
@AllArgsConstructor
@SecurityRequirement(name = "jwtAuth")
public class TaskController {
    private final TaskService taskService;

    @GetMapping(value = "/{id}/getTaskById")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @PostMapping(value = "/{rid}/addTaskToReport")
    public ResponseEntity<Task> addTask(@Valid @RequestBody Task task, @PathVariable Integer rid) {
        return ResponseEntity.ok(taskService.addTaskToReport(task, rid));
    }

    @PutMapping(value = "/updateTask")
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(task));
    }

    @DeleteMapping(value = "{id}/deleteTask")
    public ResponseEntity<String> deleteTask(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

    @PostMapping("/getNonce")
    public ResponseEntity<?> getClient(@RequestBody Map<String, String> map) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-WP-Nonce", map.get("nonce"));
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Cookie", "wordpress_logged_in_e31cf72f486e719b99049dc1c8eea8cf=" + map.get("auth"));
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://softforceapps.com/wp-json/custom/loggedinuser?_wpnonce=" + map.get("nonce");
        ResponseEntity<?> res = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        String name = res.getBody().toString().replaceAll("\"", "");
        Map<String, String> m = new HashMap<>();
        m.put("data", name);
        m.put("status", "success");
        return ResponseEntity.ok(m);
    }

    @GetMapping("/pagingAndShortingTasks/{pageNumber}/{pageSize}")
    public Page<Task> taskPagination(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
        return taskService.getTaskPagination(pageNumber, pageSize, null);
    }

}
