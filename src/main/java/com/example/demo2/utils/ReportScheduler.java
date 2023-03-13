package com.example.demo2.utils;

import com.example.demo2.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class ReportScheduler {

    @Autowired
    ReportService reportService;

    //Every Sunday 9:45 AM
    @Scheduled(cron = "0 45 9 ? * SUN")
    public void scheduleTask() {
        LocalDate today = LocalDate.now();
        LocalDate d = today.plusDays(1);
        reportService.addReportsToAllUsers(d);
    }
}
