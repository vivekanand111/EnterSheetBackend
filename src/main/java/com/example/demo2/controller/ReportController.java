package com.example.demo2.controller;

import com.example.demo2.model.Report;
import com.example.demo2.service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/report")
@Log4j2
@AllArgsConstructor
@SecurityRequirement(name = "jwtAuth")
public class ReportController {
    private final ReportService reportService;

    @PostMapping(value = "{uid}/addReportToUser")
    public ResponseEntity<Report> addReport(@Valid @RequestBody Report r, @PathVariable Integer uid){
        return ResponseEntity.ok(reportService.addReport(r,uid));
    }

    @DeleteMapping(value = "{rid}/deleteReport")
    public ResponseEntity<String> deleteReport(@PathVariable Integer rid){
        return ResponseEntity.ok(reportService.deleteReport(rid));
    }

}
