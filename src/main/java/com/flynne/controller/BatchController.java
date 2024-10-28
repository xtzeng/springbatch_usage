package com.flynne.controller;

import java.time.LocalDate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importOrderJob; // Your defined batch job

    @GetMapping("/runImportOrderJob")
    public ResponseEntity<String> runImportOrderJob() {
        try {
        /*    JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // Unique parameter to avoid job instance conflict
                    .toJobParameters();*/

            String executeDay = LocalDate.now().toString();
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("date", executeDay)
                    .toJobParameters();

            jobLauncher.run(importOrderJob, jobParameters);
            return ResponseEntity.ok("Batch job has been invoked successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Batch job failed: " + e.getMessage());
        }
    }
}
