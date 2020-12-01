package com.tryagain.batch.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ImportStepListener implements StepExecutionListener {

    private static final Logger importStepLog = LogManager.getLogger();

    @Override
    public void beforeStep(StepExecution stepExecution) {
        importStepLog.info("<---------------STARTING TO IMPORT FILE: " + stepExecution.getStepName() + "--------------->");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExitStatus status = stepExecution.getExitStatus();
        if(status.equals(ExitStatus.COMPLETED)) {
            importStepLog.info(">>>>>>>>>>>>>>>>FINISHED IMPORTING FILE: " + stepExecution.getStepName() +
                    " WITH THE FOLLOWING PARAMETERS:\nITEMS READ: " +
                    stepExecution.getReadCount() + "\nITEMS WRITTEN: " + stepExecution.getWriteCount());
        }
        return status;
    }
}
