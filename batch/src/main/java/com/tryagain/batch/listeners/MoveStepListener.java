package com.tryagain.batch.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MoveStepListener implements StepExecutionListener {

    private static final Logger moveStepLog = LogManager.getLogger();

    @Override
    public void beforeStep(StepExecution stepExecution) {
        moveStepLog.info("<---------------STARTING TO MOVE FILE: " + stepExecution.getStepName() + "--------------->");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExitStatus status = stepExecution.getExitStatus();
        if(status.equals(ExitStatus.COMPLETED)) {
            moveStepLog.info("<---------------FINISHED MOVING FILE: " + stepExecution.getStepName() + "--------------->");
        }
        return status;
    }
}
