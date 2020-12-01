package com.tryagain.batch.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobListener implements JobExecutionListener {

    private static final Logger log = LogManager.getLogger();

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("<---------------STARTING JOB FOR FILE: " + jobExecution.getJobParameters().getString("path") + "--------------->");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("<---------------FINISHED JOB FOR FILE: " + jobExecution.getJobParameters().getString("path") + "--------------->");
        }
    }
}
