package com.example.batch;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduledBatchJobRunner {
    private final JobLauncher jobLauncher;
    private final Job job;

    @Scheduled(fixedRate = 60000)
    private void runBatchJob(){
        try {
            jobLauncher.run(job,new JobParameters());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
