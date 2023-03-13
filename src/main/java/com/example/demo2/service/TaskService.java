package com.example.demo2.service;

import com.example.demo2.exception.NoRecordExistsException;
import com.example.demo2.model.Report;
import com.example.demo2.model.Task;
import com.example.demo2.repository.ReportRepository;
import com.example.demo2.repository.TaskRepository;
import com.example.demo2.request.AdminRequest;
import com.example.demo2.response.UserTasksResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ReportRepository reportRepository;

    public Task addTaskToReport(Task task, Integer rid) {
        Report r = reportRepository.findById(rid).orElseThrow(() ->
                new NoRecordExistsException("No record found with report id " + rid));
        task.setReport(r);
        return taskRepository.save(task);
    }

    public Task getTask(Integer id) {
        Task t = taskRepository.findById(id).orElseThrow(() ->
                new NoRecordExistsException("No task found with id " + id));
        return t;
    }

    public List<UserTasksResponse> getTasksBetweenDates1(AdminRequest reqBody) {
        List<Object[]> tasks = taskRepository.findAllBetweenDates1(reqBody.getStartDate(), reqBody.getEndDate());

        List<UserTasksResponse> res = tasks.stream()
                .map(objects -> {
                    return UserTasksResponse.builder()
                            .id((Integer) objects[0])
                            .project((String) objects[1])
                            .username((String) objects[2])
                            .name((String) objects[3])
                            .date((Date) objects[4])
                            .hours((Integer) objects[5])
                            .notes((String) objects[6])
                            .build();
                }).collect(Collectors.toList());
        return res;
    }

    public List<UserTasksResponse> getTasksBetweenDates2(AdminRequest reqBody) {
        List<UserTasksResponse> tasks = taskRepository.findAllBetweenDates2(reqBody.getStartDate(), reqBody.getEndDate());
        return tasks;
    }

    public Task updateTask(Task task) {
        Task t = taskRepository.findById(task.getId()).orElseThrow(() ->
                new NoRecordExistsException("No task found with id " + task.getId()));
        task.setReport(t.getReport());
        return taskRepository.save(task);
    }

    public String deleteTask(Integer id) {
        taskRepository.findById(id).orElseThrow(() ->
                new NoRecordExistsException("No task found with id " + id));
        taskRepository.deleteById(id);
        return "Deleted SuccessFully";
    }

    public Page<Task> getTaskPagination(Integer pageNumber, Integer pageSize, String sortProperty) {
        Pageable pageable = null;
        if (null != sortProperty) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sortProperty);
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return taskRepository.findAll(pageable);
    }
}
